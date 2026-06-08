package org.pokesplash.gts.command.subcommand;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.Listing;
import org.pokesplash.gts.UI.SingleListing;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.util.Utils;

import java.util.UUID;

public class Open extends Subcommand {

	public Open() {
		super("§9Usage:\n§3- gts <listing id>");
	}

	@Override
	public CommandNode<CommandSourceStack> build() {
		return Commands.argument("id", UuidArgument.uuid())
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

		ServerPlayer sender = context.getSource().getPlayer();

		UUID id = UuidArgument.getUuid(context, "id");

		Listing listing = Gts.listings.getActiveListingById(id);
		if (listing != null) {
			UIManager.openUIForcefully(sender, new SingleListing().getPage(sender, listing));
		}
		return 1;
	}
}
