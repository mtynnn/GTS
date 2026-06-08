package org.pokesplash.gts.command.subcommand;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.util.Utils;

public class Reload extends Subcommand {

	public Reload() {
		super("§9Usage:\n§3- gts reload");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("reload")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"reload");
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		Gts.reload();

		Utils.sendMsg(context.getSource(), Utils.formatPlaceholders(
				Gts.language.getReloadMessage(), 0, null, null, null));

		return 1;
	}
}
