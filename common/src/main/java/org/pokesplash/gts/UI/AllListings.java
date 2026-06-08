package org.pokesplash.gts.UI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.component.ItemLore;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.Listing;
import org.pokesplash.gts.Listing.PokemonListing;
import org.pokesplash.gts.UI.button.ExpiredListings;
import org.pokesplash.gts.UI.button.Filler;
import org.pokesplash.gts.UI.button.ManageListings;
import org.pokesplash.gts.UI.button.NextPage;
import org.pokesplash.gts.UI.button.PreviousPage;
import org.pokesplash.gts.UI.button.RelistAll;
import org.pokesplash.gts.UI.module.ListingInfo;
import org.pokesplash.gts.UI.module.PokemonInfo;
import org.pokesplash.gts.api.provider.ListingAPI;
import org.pokesplash.gts.enumeration.FilterType;
import org.pokesplash.gts.enumeration.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllListings {

	public Page getPage(FilterType filter) {

		PlaceholderButton placeholder = new PlaceholderButton();

		List<Button> buttons = new ArrayList<>();

		List<Listing> allListings = ListingAPI.getHighestPriority() == null ? Gts.listings.getListings() :
				Gts.listings.getListings().stream().map(Listing::deepClone).toList();

		List<Listing> listings = switch (filter) {
			case POKEMON -> allListings.stream().filter(Listing::isPokemon).collect(Collectors.toList());
			case ITEMS   -> allListings.stream().filter(l -> !l.isPokemon()).collect(Collectors.toList());
			default      -> allListings;
		};

		for (Listing listing : listings) {
			List<Component> lore = ListingInfo.parse(listing);

			if (listing.isPokemon()) {
				lore.addAll(PokemonInfo.parseShort((PokemonListing) listing));
			}

			Button button = GooeyButton.builder()
					.display(listing.getIcon())
					.with(DataComponents.CUSTOM_NAME, listing.getDisplayName())
					.with(DataComponents.LORE, new ItemLore(lore))
					.onClick((action) -> {
						ServerPlayer sender = action.getPlayer();
						Page page = new SingleListing().getPage(sender, listing);
						UIManager.openUIForcefully(sender, page);
					})
					.build();

			buttons.add(button);
		}

		// Filter button cycles ALL -> POKEMON -> ITEMS -> ALL
		FilterType nextFilter = switch (filter) {
			case ALL    -> FilterType.POKEMON;
			case POKEMON -> FilterType.ITEMS;
			case ITEMS  -> FilterType.ALL;
		};

		List<String> filterLore = switch (filter) {
			case ALL    -> Gts.language.getFilterButtonLoreAll();
			case POKEMON -> Gts.language.getFilterButtonLorePokemon();
			case ITEMS  -> Gts.language.getFilterButtonLoreItems();
		};

		net.minecraft.world.item.ItemStack filterItem = switch (filter) {
			case ALL    -> Gts.language.getFilterAllButtonItem();
			case POKEMON -> Gts.language.getFilterPokemonButtonItem();
			case ITEMS  -> Gts.language.getFilterItemsButtonItem();
		};

		Button filterButton = GooeyButton.builder()
				.display(filterItem)
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getFilterButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						filterLore.stream().map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new AllListings().getPage(nextFilter));
				})
				.build();

		Button refreshButton = GooeyButton.builder()
				.display(Gts.language.getRefreshButtonItem())
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getRefreshButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						Gts.language.getRefreshButtonLore().stream()
								.map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new AllListings().getPage(filter));
				})
				.build();

		Button infoButton = GooeyButton.builder()
				.display(Gts.language.getInformationButtonItem())
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getInformationButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						Gts.language.getInformationButtonLore().stream()
								.map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.build();

		// Sort button (cycles DATE -> PRICE -> NAME -> DATE), default DATE on main page
		Button sortButton = buildSortButton(Sort.NONE, filter);

		ChestTemplate template = ChestTemplate.builder(6)
				.rectangle(0, 0, 5, 9, placeholder)
				.fill(Filler.getButton())
				.set(45, ManageListings.getButton())
				.set(46, filterButton)
				.set(47, sortButton)
				.set(48, PreviousPage.getButton())
				.set(49, refreshButton)
				.set(50, NextPage.getButton())
				.set(51, ExpiredListings.getButton())
				.set(52, RelistAll.getButton())
				.set(53, infoButton)
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
		page.setTitle(Gts.language.getTitle());
		setPageTitle(page);

		return page;
	}

	private Button buildSortButton(Sort currentSort, FilterType filter) {
		Sort nextSort = switch (currentSort) {
			case NONE, NAME -> Sort.DATE;
			case DATE       -> Sort.PRICE;
			case PRICE      -> Sort.NAME;
		};

		List<String> sortLore = switch (currentSort) {
			case PRICE      -> Gts.language.getSortButtonLorePrice();
			case NAME       -> Gts.language.getSortButtonLoreName();
			default         -> Gts.language.getSortButtonLoreDate();
		};

		net.minecraft.world.item.ItemStack sortItem = switch (currentSort) {
			case PRICE -> Gts.language.getSortByPriceButtonItem();
			case NAME  -> Gts.language.getSortByNameButtonItem();
			default    -> Gts.language.getSortByNewestButtonItem();
		};

		return GooeyButton.builder()
				.display(sortItem)
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getSortByNewestButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						sortLore.stream().map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new AllListings().getPageSorted(filter, nextSort));
				})
				.build();
	}

	public Page getPageSorted(FilterType filter, Sort sort) {

		PlaceholderButton placeholder = new PlaceholderButton();
		List<Button> buttons = new ArrayList<>();

		List<Listing> allListings = ListingAPI.getHighestPriority() == null ? Gts.listings.getListings() :
				Gts.listings.getListings().stream().map(Listing::deepClone).toList();

		List<Listing> listings = switch (filter) {
			case POKEMON -> allListings.stream().filter(Listing::isPokemon).collect(Collectors.toList());
			case ITEMS   -> allListings.stream().filter(l -> !l.isPokemon()).collect(Collectors.toList());
			default      -> new ArrayList<>(allListings);
		};

		switch (sort) {
			case PRICE -> listings.sort(java.util.Comparator.comparingDouble(Listing::getPrice));
			case DATE  -> listings.sort(java.util.Comparator.comparingLong(Listing::getEndTime));
			case NAME  -> listings.sort(java.util.Comparator.comparing(Listing::getListingName));
			default -> {}
		}

		for (Listing listing : listings) {
			List<Component> lore = ListingInfo.parse(listing);
			if (listing.isPokemon()) {
				lore.addAll(PokemonInfo.parseShort((PokemonListing) listing));
			}
			Button button = GooeyButton.builder()
					.display(listing.getIcon())
					.with(DataComponents.CUSTOM_NAME, listing.getDisplayName())
					.with(DataComponents.LORE, new ItemLore(lore))
					.onClick((action) -> {
						ServerPlayer sender = action.getPlayer();
						Page page = new SingleListing().getPage(sender, listing);
						UIManager.openUIForcefully(sender, page);
					})
					.build();
			buttons.add(button);
		}

		FilterType nextFilter = switch (filter) {
			case ALL    -> FilterType.POKEMON;
			case POKEMON -> FilterType.ITEMS;
			case ITEMS  -> FilterType.ALL;
		};

		List<String> filterLore = switch (filter) {
			case ALL    -> Gts.language.getFilterButtonLoreAll();
			case POKEMON -> Gts.language.getFilterButtonLorePokemon();
			case ITEMS  -> Gts.language.getFilterButtonLoreItems();
		};

		net.minecraft.world.item.ItemStack filterItem = switch (filter) {
			case ALL    -> Gts.language.getFilterAllButtonItem();
			case POKEMON -> Gts.language.getFilterPokemonButtonItem();
			case ITEMS  -> Gts.language.getFilterItemsButtonItem();
		};

		Button filterButton = GooeyButton.builder()
				.display(filterItem)
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getFilterButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						filterLore.stream().map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new AllListings().getPageSorted(nextFilter, sort));
				})
				.build();

		Button refreshButton = GooeyButton.builder()
				.display(Gts.language.getRefreshButtonItem())
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getRefreshButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						Gts.language.getRefreshButtonLore().stream()
								.map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					UIManager.openUIForcefully(sender, new AllListings().getPageSorted(filter, sort));
				})
				.build();

		Button infoButton = GooeyButton.builder()
				.display(Gts.language.getInformationButtonItem())
				.with(DataComponents.CUSTOM_NAME, org.pokesplash.gts.util.ColorUtil.parse(Gts.language.getInformationButtonLabel()))
				.with(DataComponents.LORE, new ItemLore(
						Gts.language.getInformationButtonLore().stream()
								.map(org.pokesplash.gts.util.ColorUtil::parse).collect(Collectors.toList())))
				.build();

		Button sortButton = buildSortButton(sort, filter);

		ChestTemplate template = ChestTemplate.builder(6)
				.rectangle(0, 0, 5, 9, placeholder)
				.fill(Filler.getButton())
				.set(45, ManageListings.getButton())
				.set(46, filterButton)
				.set(47, sortButton)
				.set(48, PreviousPage.getButton())
				.set(49, refreshButton)
				.set(50, NextPage.getButton())
				.set(51, ExpiredListings.getButton())
				.set(52, RelistAll.getButton())
				.set(53, infoButton)
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
		page.setTitle(Gts.language.getTitle());
		setPageTitle(page);

		return page;
	}

	private void setPageTitle(LinkedPage page) {
		LinkedPage next = page.getNext();
		if (next != null) {
			next.setTitle(Gts.language.getTitle());
			setPageTitle(next);
		}
	}
}
