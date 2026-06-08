package org.pokesplash.gts.UI.button;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ItemLore;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.util.ColorUtil;

import java.util.stream.Collectors;

public abstract class NextPage {
    public static Button getButton() {
        return LinkedPageButton.builder()
                .linkType(LinkType.Next)
                .display(Gts.language.getNextPageButtonItems())
                .with(DataComponents.CUSTOM_NAME,
                        ColorUtil.parse(Gts.language.getNextPageButtonLabel()))
                .with(DataComponents.LORE, new ItemLore(
                        Gts.language.getNextPageButtonLore().stream()
                                .map(ColorUtil::parse)
                                .collect(Collectors.toList())))
                .build();
    }
}
