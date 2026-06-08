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
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.NotNull;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.PokemonListing;
import org.pokesplash.gts.UI.button.ExpiredListings;
import org.pokesplash.gts.UI.button.Filler;
import org.pokesplash.gts.UI.button.NextPage;
import org.pokesplash.gts.UI.button.PreviousPage;
import org.pokesplash.gts.UI.button.RelistAll;
import org.pokesplash.gts.UI.module.ListingInfo;
import org.pokesplash.gts.UI.module.PokemonInfo;
import org.pokesplash.gts.enumeration.FilterType;
import org.pokesplash.gts.enumeration.Sort;
import org.pokesplash.gts.util.ColorUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonListings {

	public Page getPage(@NotNull Sort sort) {

		List<PokemonListing> pkmListings = Gts.listings.getPokemonListings();

		if (sort.equals(Sort.PRICE)) {
			pkmListings.sort(Comparator.comparingDouble(PokemonListing::getPrice));
		} else if (sort.equals(Sort.DATE)) {
			pkmListings.sort(Comparator.comparingLong(PokemonListing::getEndTime));
		} else if (sort.equals(Sort.NAME)) {
			pkmListings.sort(Comparator.comparing(PokemonListing::getListingName));
		}

		Sort nextSort = switch (sort) {
			case NONE, NAME -> Sort.DATE;
			case DATE       -> Sort.PRICE;
			case PRICE      -> Sort.NAME;
		};

		List<String> sortLore = switch (sort) {
			case PRICE -> Gts.language.getSortButtonLorePrice();
			case NAME  -> Gts.language.getSortButtonLoreName();
			default    -> Gts.language.getSortButtonLoreDate();
		};

		net.minecraft.world.item.ItemStack sortItem = switch (sort) {
			case PRICE -> Gts.language.getSortByPriceButtonItem();
			case NAME  -> Gts.language.getSortByNameButtonItem();
			default    -> Gts.language.getSortByNewestButtonItem();
		};

		Button sortButton = GooeyButton.builder()
				.display(sortItem)
				.with(DataComponents.CUSTOM_NAME, ColorUtil.parse(Gts.language.getSortByNewestButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						sortLore.stream().map(ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new PokemonListings().getPage(nextSort));
				})
				.build();

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
					UIManager.openUIForcefully(sender, new PokemonListings().getPage(sort));
				})
				.build();

		PlaceholderButton placeholder = new PlaceholderButton();

		List<Button> pokemonButtons = new ArrayList<>();
		for (PokemonListing listing : pkmListings) {
			List<Component> lore = ListingInfo.parse(listing);
			lore.addAll(PokemonInfo.parseShort(listing));

			Button button = GooeyButton.builder()
					.display(PokemonItem.from(listing.getListing(), 1))
					.with(DataComponents.CUSTOM_NAME, listing.getDisplayName())
					.with(DataComponents.LORE, new ItemLore(lore))
					.onClick((action) -> {
						ServerPlayer sender = action.getPlayer();
						Page page = new SingleListing().getPage(sender, listing);
						UIManager.openUIForcefully(sender, page);
					})
					.build();
			pokemonButtons.add(button);
		}

		ChestTemplate template = ChestTemplate.builder(6)
				.rectangle(0, 0, 5, 9, placeholder)
				.fill(Filler.getButton())
				.set(45, backButton)
				.set(46, sortButton)
				.set(48, PreviousPage.getButton())
				.set(49, refreshButton)
				.set(50, NextPage.getButton())
				.set(51, ExpiredListings.getButton())
				.set(52, RelistAll.getButton())
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, pokemonButtons, null);
		page.setTitle(Gts.language.getPokemonListingsTitle());
		setPageTitle(page);

		return page;
	}

	private void setPageTitle(LinkedPage page) {
		LinkedPage next = page.getNext();
		if (next != null) {
			next.setTitle(Gts.language.getPokemonListingsTitle());
			setPageTitle(next);
		}
	}
}
