package org.pokesplash.gts.command.subcommand;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.Page;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.util.Utils;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.ManageListings;
import org.pokesplash.gts.command.superclass.Subcommand;

public class Manage extends Subcommand {

	public Manage() {
		super("§9Usage:\n§3- gts manage");
	}

	/**
	 * Method used to add to the base command for this subcommand.
	 * @return source to complete the command.
	 */
	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("manage")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"manage");
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	/**
	 * Method to perform the logic when the command is executed.
	 * @param context the source of the command.
	 * @return integer to complete command.
	 */
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		if (!context.getSource().isPlayer()) {
			Utils.sendMsg(context.getSource(), Gts.language.getPlayerOnly());
			return 1;
		}

		ServerPlayer sender = context.getSource().getPlayer();

		Page page = new ManageListings().getPage(sender.getUUID());

		UIManager.openUIForcefully(sender, page);

		return 1;
	}
}
