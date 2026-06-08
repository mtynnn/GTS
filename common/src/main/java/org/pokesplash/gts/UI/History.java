package org.pokesplash.gts.UI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.item.PokemonItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.ItemLore;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.button.Filler;
import org.pokesplash.gts.UI.button.NextPage;
import org.pokesplash.gts.UI.button.PreviousPage;
import org.pokesplash.gts.UI.module.PokemonInfo;
import org.pokesplash.gts.enumeration.FilterType;
import org.pokesplash.gts.history.HistoryItem;
import org.pokesplash.gts.history.ItemHistoryItem;
import org.pokesplash.gts.history.PlayerHistory;
import org.pokesplash.gts.history.PokemonHistoryItem;
import org.pokesplash.gts.util.ColorUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class History {

	public Page getPage(UUID owner) {

		PlaceholderButton placeholder = new PlaceholderButton();

		PlayerHistory playerHistory = Gts.history.getPlayerHistory(owner);

		List<Button> buttons = new ArrayList<>();

		if (playerHistory != null) {
			List<HistoryItem> items = playerHistory.getListings();
			items.sort(Comparator.comparing(HistoryItem::getSoldDate));
			Collections.reverse(items);

			for (HistoryItem item : items) {

				List<Component> lore = new ArrayList<>();

				lore.add(ColorUtil.parse(Gts.language.getSeller() + item.getSellerName()));
				lore.add(ColorUtil.parse(Gts.language.getPrice() + item.getPriceAsString()));
				lore.add(ColorUtil.parse(Gts.language.getBuyer() + item.getBuyerName()));

				String pattern = "d MMMM yyyy";
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				lore.add(ColorUtil.parse(Gts.language.getSold_date() +
						format.format(new Date(item.getSoldDate()))));

				Button button = null;

				if (item.isPokemon()) {
					PokemonHistoryItem pokemonItem = (PokemonHistoryItem) item;
					lore.addAll(PokemonInfo.parseShort(pokemonItem.getListing()));

					button = GooeyButton.builder()
							.display(PokemonItem.from(pokemonItem.getListing(), 1))
							.with(DataComponents.CUSTOM_NAME, pokemonItem.getDisplayName())
							.with(DataComponents.LORE, new ItemLore(lore))
							.build();
				} else {
					ItemHistoryItem itemHistoryItem = (ItemHistoryItem) item;

					if (itemHistoryItem.getListing() != null) {
						button = GooeyButton.builder()
								.display(itemHistoryItem.getListing())
								.with(DataComponents.CUSTOM_NAME, itemHistoryItem.getDisplayName())
								.with(DataComponents.LORE, new ItemLore(lore))
								.with(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE)
								.build();
					}
				}

				if (button != null) {
					buttons.add(button);
				}
			}
		}

		Button backButton = GooeyButton.builder()
				.display(Gts.language.getBackButtonItem())
				.with(DataComponents.CUSTOM_NAME, ColorUtil.parse(Gts.language.getBackButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						Gts.language.getBackButtonLore().stream()
								.map(ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new AllListings().getPage(FilterType.ALL));
				})
				.build();

		Button refreshButton = GooeyButton.builder()
				.display(Gts.language.getRefreshButtonItem())
				.with(DataComponents.CUSTOM_NAME, ColorUtil.parse(Gts.language.getRefreshButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						Gts.language.getRefreshButtonLore().stream()
								.map(ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new History().getPage(sender.getUUID()));
				})
				.build();

		ChestTemplate template = ChestTemplate.builder(6)
				.rectangle(0, 0, 5, 9, placeholder)
				.fill(Filler.getButton())
				.set(45, backButton)
				.set(48, PreviousPage.getButton())
				.set(49, refreshButton)
				.set(50, NextPage.getButton())
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
		page.setTitle(Gts.language.getHistoryTitle());
		setPageTitle(page);

		return page;
	}

	private void setPageTitle(LinkedPage page) {
		LinkedPage next = page.getNext();
		if (next != null) {
			next.setTitle(Gts.language.getHistoryTitle());
			setPageTitle(next);
		}
	}
}
