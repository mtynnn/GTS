package org.pokesplash.gts.UI.module;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.ItemListing;
import org.pokesplash.gts.Listing.Listing;
import org.pokesplash.gts.util.ColorUtil;
import org.pokesplash.gts.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ListingInfo {

    public static List<Component> parse(Listing listing) {
        List<Component> lore = new ArrayList<>();

        if (!listing.isPokemon()) {
            ItemListing itemListing = (ItemListing) listing;
            List<String> blockedItemDescriptions = Gts.config.getRemovedModDescriptions();

            boolean isItemBlocked = false;
            for (String blockedItemId : blockedItemDescriptions) {
                if (itemListing.getListing().getItem().getDescriptionId().contains(blockedItemId)) {
                    isItemBlocked = true;
                    break;
                }
            }

            if (!isItemBlocked) {
                try {
                    List<Component> itemTooltips = itemListing.getListing()
                            .getTooltipLines(Item.TooltipContext.EMPTY, null, TooltipFlag.NORMAL);
                    lore.addAll(itemTooltips.subList(1, itemTooltips.size()));
                } catch (Exception ignored) {}
            }
        }

        lore.add(Component.empty());
        lore.add(ColorUtil.parse(Gts.language.getPrice() + listing.getPriceAsString()));
        lore.add(ColorUtil.parse(Gts.language.getSeller() + listing.getSellerName()));

        if (listing.getEndTime() != -1 && listing.getEndTime() > new Date().getTime()) {
            lore.add(ColorUtil.parse(Gts.language.getRemainingTime() +
                            Utils.parseLongDate(listing.getEndTime() - new Date().getTime())));
        }

        lore.add(Component.empty());
        lore.add(ColorUtil.parse("&e→ Click para ver detalle"));

        return lore;
    }
}
