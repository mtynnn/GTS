package org.pokesplash.gts.command.subcommand;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.util.Utils;

public class GetPrice extends Subcommand {

	public GetPrice() {
		super("§9Usage:\n§3- gts getprice [slot]");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("getprice")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"getprice");
					} else {
						return true;
					}
				})
				.executes(this::runItem)
					.then(Commands.argument("slot", IntegerArgumentType.integer(1, 6))
					.executes(this::runPokemon))
				.build();
	}

	@Override
	public int run(CommandContext<CommandSourceStack> context) {
		return 0;
	}

	public int runItem(CommandContext<CommandSourceStack> context) {
		if (!context.getSource().isPlayer()) {
			Utils.sendMsg(context.getSource(), Gts.language.getPlayerOnly());
			return 1;
		}

		try {
			ServerPlayer sender = context.getSource().getPlayer();

			ItemStack item = sender.getMainHandItem();

			if (item.isEmpty()) {
				Utils.sendMsg(sender, Gts.language.getMustHoldItem());
				return 1;
			}

			double price = Gts.history.getAveragePrice(item);

			if (price != 0) {
				Utils.sendMsg(sender, Gts.language.getAveragePrice().replace("{price}", String.valueOf(price)));
			} else {
				Utils.sendMsg(sender, Gts.language.getNoSalesHistory());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int runPokemon(CommandContext<CommandSourceStack> context) {
		if (!context.getSource().isPlayer()) {
			Utils.sendMsg(context.getSource(), Gts.language.getPlayerOnly());
			return 1;
		}

		ServerPlayer sender = context.getSource().getPlayer();

		int slot = IntegerArgumentType.getInteger(context, "slot");

		Pokemon pokemon = Cobblemon.INSTANCE.getStorage().getParty(sender).get(slot - 1);

		if (pokemon == null) {
			Utils.sendMsg(sender, Gts.language.getNoPokemonInSlot());
			return 1;
		}

		double price = Gts.history.getAveragePrice(pokemon);

		if (price != 0) {
			Utils.sendMsg(sender, Gts.language.getAveragePrice().replace("{price}", String.valueOf(price)));
		} else {
			Utils.sendMsg(sender, Gts.language.getNoSalesHistory());
		}
		return 1;
	}
}
