package org.pokesplash.gts.command.subcommand;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonElement;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.ItemListing;
import org.pokesplash.gts.Listing.Listing;
import org.pokesplash.gts.Listing.PokemonListing;
import org.pokesplash.gts.api.GtsAPI;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.config.options.ItemPrices;
import org.pokesplash.gts.config.options.PokemonAspects;
import org.pokesplash.gts.config.options.PokemonPrices;
import org.pokesplash.gts.util.CodecUtils;
import org.pokesplash.gts.util.Utils;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class List extends Subcommand {

	public List() {
		super("§9Uso:\n§3- /gts sell <precio>\n§3- /gts sell pokemon <slot> <precio>");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("sell")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(), "sell");
					} else {
						return true;
					}
				})
				.executes(this::showUsage)
				// /gts sell pokemon <slot> <precio> — literal primero para que Brigadier lo resuelva antes que el argumento float
				.then(Commands.literal("pokemon")
						.requires(ctx -> {
							if (ctx.isPlayer()) {
								return Gts.config.isEnablePokemonSales();
							} else {
								return false;
							}
						})
						.executes(this::showPokemonUsage)
						.then(Commands.argument("slot", IntegerArgumentType.integer(1, 6))
								.suggests((ctx, builder) -> {
									for (int x = 1; x <= 6; x++) {
										builder.suggest(x);
									}
									return builder.buildFuture();
								})
								.executes(this::showPokemonUsage)
								.then(Commands.argument("price", FloatArgumentType.floatArg())
										.suggests((ctx, builder) -> {
											for (double price : Gts.config.getAllPokemonPrices()) {
												if (price > 0) {
													builder.suggest((int) price);
												}
											}
											return builder.buildFuture();
										})
										.executes(this::runPokemon))))
				// /gts sell <precio> — item en mano (después del literal pokemon)
				.then(Commands.argument("price", FloatArgumentType.floatArg())
						.requires(ctx -> {
							if (ctx.isPlayer()) {
								return Gts.config.isEnableItemSales();
							} else {
								return false;
							}
						})
						.suggests((ctx, builder) -> {
							for (int i = 1; i <= 11; i++) {
								builder.suggest(i * 100);
							}
							return builder.buildFuture();
						})
						.executes(this::runItem))
				.build();
	}

	@Override
	public int run(CommandContext<CommandSourceStack> context) {
		return showUsage(context);
	}

	private boolean checkCommonRestrictions(CommandContext<CommandSourceStack> context) {
		if (!Gts.timeouts.hasTimeoutExpired(context.getSource().getPlayer().getUUID())) {
			long endTime = Gts.timeouts.getTimeout(context.getSource().getPlayer().getUUID());
			Utils.sendMsg(context.getSource(), Gts.language.getTimedOut()
					.replace("{time}", Utils.parseLongDate(endTime - new Date().getTime())));
			return false;
		}

		PokemonBattle battle = BattleRegistry.getBattleByParticipatingPlayer(context.getSource().getPlayer());
		if (battle != null) {
			Utils.sendMsg(context.getSource(), Gts.language.getInBattle());
			return false;
		}

		int totalListings = Gts.listings.getListingsByPlayer(context.getSource().getPlayer().getUUID()).size();
		java.util.List<Listing> expiredListings = Gts.listings.getExpiredListingsOfPlayer(
				context.getSource().getPlayer().getUUID());
		int totalExpired = expiredListings == null ? 0 : expiredListings.size();

		if (totalListings + totalExpired >= Gts.config.getMaxListingsPerPlayer()) {
			Utils.sendMsg(context.getSource(), Utils.formatPlaceholders(Gts.language.getMaximumListings(),
					0, null, context.getSource().getPlayer().getDisplayName().getString(), null));
			return false;
		}

		return true;
	}

	public int runItem(CommandContext<CommandSourceStack> context) {
		if (!context.getSource().isPlayer()) {
			Utils.sendMsg(context.getSource(), Gts.language.getPlayerOnly());
			return 1;
		}

		if (!checkCommonRestrictions(context)) return 1;

		ServerPlayer player = context.getSource().getPlayer();
		double price = FloatArgumentType.getFloat(context, "price");

		java.util.List<ItemPrices> minPrices = Gts.config.getCustomItemPrices();
		java.util.List<JsonElement> bannedItems = Gts.config.getBannedItems();

		try {
			ItemStack item = player.getMainHandItem();

			if (item == null || item.isEmpty()) {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getNoItemInHand(),
						0, null, player.getDisplayName().getString(), null));
				return 1;
			}

			for (JsonElement bannedItem : bannedItems) {
				ItemStack banned = CodecUtils.decodeItem(bannedItem);
				if (banned.getItem().equals(item.getItem()) &&
						ItemStack.isSameItemSameComponents(banned, item)) {
					Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getBannedItem(),
							0, item.getDisplayName().getString(), player.getDisplayName().getString(), null));
					return 1;
				}
			}

			double minPrice = 0;

			for (ItemPrices minItem : minPrices) {
				ItemStack min = CodecUtils.decodeItem(minItem.getItem());
				if (min.getItem().equals(item.getItem()) &&
						ItemStack.isSameItemSameComponents(min, item)) {
					minPrice += minItem.getMinPrice();
					break;
				}
			}

			CustomData customData = item.get(DataComponents.CUSTOM_DATA);
			if (customData != null && customData.contains("ivs")) {
				CompoundTag tag = customData.copyTag();
				AtomicInteger totalMaxIVs = new AtomicInteger();
				Arrays.stream(tag.getIntArray("ivs")).forEach(i -> {
					if (i == 31) totalMaxIVs.getAndIncrement();
				});
				switch (totalMaxIVs.get()) {
					case 1: minPrice += Gts.config.getMinPrice1IV(); break;
					case 2: minPrice += Gts.config.getMinPrice2IV(); break;
					case 3: minPrice += Gts.config.getMinPrice3IV(); break;
					case 4: minPrice += Gts.config.getMinPrice4IV(); break;
					case 5: minPrice += Gts.config.getMinPrice5IV(); break;
					case 6: minPrice += Gts.config.getMinPrice6IV(); break;
					default: break;
				}
			}

			if (price < minPrice) {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getMinimumListingPrice(),
						minPrice, item.getDisplayName().getString(), player.getDisplayName().getString(), null));
				return 1;
			}

			if (price > Gts.config.getMaximumPrice()) {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getMaximumListingPrice(),
						minPrice, item.getDisplayName().getString(), player.getDisplayName().getString(), null));
				return 1;
			}

			int amount = item.getCount();

			// Check max listings before creating
			int totalActiveListings = Gts.listings.getListingsByPlayer(player.getUUID()).size();
			int totalExpiredListings = Gts.listings.getExpiredListingsOfPlayer(player.getUUID()).size();
			if (totalActiveListings + totalExpiredListings >= Gts.config.getMaxListingsPerPlayer()) {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getMaximumListings(), 0, null,
						player.getDisplayName().getString(), null));
				return 1;
			}

			ItemStack listingItem = item.copy();
			listingItem.setCount(amount);

			ItemListing listing = new ItemListing(player.getUUID(), player.getName().getString(), price, listingItem);

			boolean success = GtsAPI.addListing(listing, player, null);

			if (success) {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getListingSuccess(),
						minPrice, listing.getListingName(), player.getDisplayName().getString(), null));
			} else {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getListingFail(),
						minPrice, listing.getListingName(), player.getDisplayName().getString(), null));
			}

			return 1;

		} catch (NullPointerException e) {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getItemIdNotFound(),
					0, null, player.getDisplayName().getString(), null));
			Gts.LOGGER.error("Couldn't find Item ID\n Stacktrace: ");
			e.printStackTrace();
			return 1;
		}
	}

	public int runPokemon(CommandContext<CommandSourceStack> context) {
		if (!context.getSource().isPlayer()) {
			Utils.sendMsg(context.getSource(), Gts.language.getPlayerOnly());
			return 1;
		}

		if (!checkCommonRestrictions(context)) return 1;

		ServerPlayer player = context.getSource().getPlayer();

		int slot = IntegerArgumentType.getInteger(context, "slot") - 1;
		double price = FloatArgumentType.getFloat(context, "price");

		PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
		Pokemon pokemon = party.get(slot);

		if (pokemon == null) {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getNoPokemonInSlot(),
					0, null, player.getDisplayName().getString(), null));
			return 1;
		}

		if (!pokemon.getTradeable()) {
			Utils.sendMsg(player, Gts.language.getNotTradeable());
			return 1;
		}

		if (party.occupied() < 2) {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getOnlyOnePokemonInParty(),
					0, null, player.getDisplayName().getString(), null));
			return 1;
		}

		AtomicInteger totalMaxIvs = new AtomicInteger();
		pokemon.getIvs().forEach((stat) -> {
			if (stat.getValue() == 31) totalMaxIvs.addAndGet(1);
		});

		double minPrice = 0;
		switch (totalMaxIvs.get()) {
			case 1: minPrice += Gts.config.getMinPrice1IV(); break;
			case 2: minPrice += Gts.config.getMinPrice2IV(); break;
			case 3: minPrice += Gts.config.getMinPrice3IV(); break;
			case 4: minPrice += Gts.config.getMinPrice4IV(); break;
			case 5: minPrice += Gts.config.getMinPrice5IV(); break;
			case 6: minPrice += Gts.config.getMinPrice6IV(); break;
		}

		if (Utils.isHA(pokemon)) minPrice += Gts.config.getMinPriceHA();
		if (pokemon.isLegendary()) minPrice += Gts.config.getMinPriceLegendary();
		if (pokemon.isUltraBeast()) minPrice += Gts.config.getMinPriceUltrabeast();

		java.util.List<PokemonPrices> minPrices = Gts.config.getCustomPokemonPrices();
		for (PokemonPrices pokemonPrices : minPrices) {
			if (pokemonPrices.getPokemon().equals(pokemon)) {
				minPrice += pokemonPrices.getPrice();
				break;
			}
		}

		if (price < minPrice) {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getMinimumListingPrice(),
					minPrice, pokemon.getDisplayName(Gts.showPokemonDisplayName).getString(), player.getDisplayName().getString(), null));
			return 1;
		}

		if (price > Gts.config.getMaximumPrice()) {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getMaximumListingPrice(),
					minPrice, pokemon.getDisplayName(Gts.showPokemonDisplayName).getString(), player.getDisplayName().getString(), null));
			return 1;
		}

		java.util.List<PokemonAspects> bannedPokemon = Gts.config.getBannedPokemon();
		for (PokemonAspects bannedMon : bannedPokemon) {
			if (bannedMon.equals(pokemon)) {
				Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getBannedPokemon(),
						0, pokemon.getSpecies().getName(), player.getDisplayName().getString(), null));
				return 1;
			}
		}

		PokemonListing listing = new PokemonListing(player.getUUID(), player.getName().getString(), price, pokemon);

		boolean success = GtsAPI.addListing(listing, player, slot);

		if (success) {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getListingSuccess(),
					minPrice, pokemon.getDisplayName(Gts.showPokemonDisplayName).getString(), player.getDisplayName().getString(), null));
		} else {
			Utils.sendMsg(player, Utils.formatPlaceholders(Gts.language.getListingFail(),
					minPrice, pokemon.getDisplayName(Gts.showPokemonDisplayName).getString(), player.getDisplayName().getString(), null));
		}

		return 1;
	}

	public int showPokemonUsage(CommandContext<CommandSourceStack> context) {
		context.getSource().sendSystemMessage(net.minecraft.network.chat.Component.literal(
				Utils.formatMessage("§9Uso:\n§3- /gts sell pokemon <slot> <precio>", context.getSource().isPlayer())));
		return 1;
	}
}
