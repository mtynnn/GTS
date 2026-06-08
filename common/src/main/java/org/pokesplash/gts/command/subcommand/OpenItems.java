package org.pokesplash.gts.command.subcommand;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.Page;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.ItemListings;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.enumeration.Sort;
import org.pokesplash.gts.util.Utils;

public class OpenItems extends Subcommand {

	public OpenItems() {
		super("§9Usage:\n§3- gts item");
	}

	@Override
	public CommandNode<CommandSourceStack> build() {
		return Commands.literal("item")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"base");
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

		ServerPlayer player = context.getSource().getPlayer();

		Page page = new ItemListings().getPage(Sort.NONE);

		UIManager.openUIForcefully(player, page);
		return 1;
	}
}
