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
import org.pokesplash.gts.UI.button.Filler;
import org.pokesplash.gts.UI.button.NextPage;
import org.pokesplash.gts.UI.button.PreviousPage;
import org.pokesplash.gts.UI.module.ListingInfo;
import org.pokesplash.gts.UI.module.PokemonInfo;
import org.pokesplash.gts.api.provider.ListingAPI;
import org.pokesplash.gts.enumeration.FilterType;
import org.pokesplash.gts.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FilteredListings {

	public Page getPage(String searchValue) {

		PlaceholderButton placeholder = new PlaceholderButton();

		List<Button> buttons = new ArrayList<>();

		List<Listing> listings = ListingAPI.getHighestPriority() == null ? Gts.listings.getListings() :
				Gts.listings.getListings().stream().map(Listing::deepClone).toList();

		for (Listing listing : listings) {

			if (listing.getListingName().toLowerCase(Locale.ROOT).contains(searchValue.toLowerCase(Locale.ROOT))
				|| listing.getSellerName().toLowerCase(Locale.ROOT).contains(searchValue.toLowerCase(Locale.ROOT))) {
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
					UIManager.openUIForcefully(sender, new FilteredListings().getPage(searchValue));
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
		page.setTitle(Gts.language.getFilteredListingsTitle().replaceAll("%search%", searchValue));
		setPageTitle(page, searchValue);

		return page;
	}

	private void setPageTitle(LinkedPage page, String searchValue) {
		LinkedPage next = page.getNext();
		if (next != null) {
			next.setTitle(Gts.language.getFilteredListingsTitle().replaceAll("%search%", searchValue));
			setPageTitle(next, searchValue);
		}
	}
}
