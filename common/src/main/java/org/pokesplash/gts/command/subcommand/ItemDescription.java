package org.pokesplash.gts.command.subcommand;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.util.Utils;

public class ItemDescription extends Subcommand {

	public ItemDescription() {
		super("§9Usage:\n§3- gts itemdesc");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("itemdesc")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"itemdesc");
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		if (!context.getSource().isPlayer()) {
			Utils.sendMsg(context.getSource(), Gts.language.getPlayerOnly());
			return 1;
		}

		ItemStack handItem = context.getSource().getPlayer().getMainHandItem();

		if (handItem.is(Items.AIR)) {
			Utils.sendMsg(context.getSource(), Gts.language.getMustHoldItem());
			return 1;
		}

		Utils.sendMsg(context.getSource(), Gts.language.getItemDescriptionMessage()
				.replace("{item}", handItem.getItem().getDescriptionId()));

		return 1;
	}
}
