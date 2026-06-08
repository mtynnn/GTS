package org.pokesplash.gts.command.subcommand;

import com.google.gson.JsonElement;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.serialization.JsonOps;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.command.superclass.Subcommand;
import org.pokesplash.gts.util.Utils;

import java.util.concurrent.CompletableFuture;

public class SaveItem extends Subcommand {

	public SaveItem() {
		super("§9Usage:\n§3- gts saveitem");
	}

	@Override
	public LiteralCommandNode<CommandSourceStack> build() {
		return Commands.literal("saveitem")
				.requires(ctx -> {
					if (ctx.isPlayer()) {
						return Gts.permissions.hasPermission(ctx.getPlayer(),
								"saveitem");
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

		try {
			ItemStack item = context.getSource().getPlayer().getMainHandItem();

			if (item.getItem().equals(Items.AIR)) {
				Utils.sendMsg(context.getSource(), Gts.language.getMustHoldItem());
				return 1;
			}

			JsonElement jsonElement = ItemStack.CODEC.encodeStart(Gts.server.registryAccess().createSerializationContext(JsonOps.INSTANCE),
					item).getOrThrow();
			String data = Utils.newGson().toJson(jsonElement);
			CompletableFuture<Boolean> result = Utils.writeFileAsync("/config/gts/", "item.json", data);

			if (result.join()) {
				Utils.sendMsg(context.getSource(), Gts.language.getSaveItemSuccess());
			} else {
				Utils.sendMsg(context.getSource(), Gts.language.getSaveItemFail());
			}
		} catch (Exception e) {
			Utils.sendMsg(context.getSource(), Gts.language.getSomethingWentWrong());
			e.printStackTrace();
		}

		return 1;
	}
}
