package org.pokesplash.gts.command.subcommand;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.util.Utils;

public class Debug extends Subcommand {

	public Debug() {
		super("§9Usage:\n§3- gts debug");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("debug")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"debug");
					} else {
						return true;
					}
				})
				.executes(this::run)
				.build();
	}

	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		Gts.isDebugMode = !Gts.isDebugMode;

		Utils.sendMsg(context.getSource(), Gts.language.getDebugModeSet()
				.replace("{value}", String.valueOf(Gts.isDebugMode)));

		return 1;
	}
}
