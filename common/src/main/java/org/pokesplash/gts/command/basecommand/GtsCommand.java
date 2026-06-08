package org.pokesplash.gts.command.basecommand;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.Page;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.AllListings;
import org.pokesplash.gts.enumeration.FilterType;
import org.pokesplash.gts.command.subcommand.*;
import org.pokesplash.gts.util.Utils;
import org.pokesplash.gts.command.superclass.BaseCommand;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Creates the mods base command.
 */
public class GtsCommand extends BaseCommand {

	private static final ScheduledExecutorService ASYNC_EXEC = Executors.newScheduledThreadPool(2,
			new ThreadFactoryBuilder().setNameFormat("GTS-Search-Thread-#%d").setDaemon(true).build());

	public GtsCommand() {
		super("gts", Arrays.asList("ah", "auction", "auctions", "subasta", "subastas"),
				"base",
				Arrays.asList(new Manage(), new Expired(), new List(), new History(),
						new Reload(), new Open(), new Debug(), new Search(), new GetPrice(), new Timeout(),
						new SaveItem(), new ItemDescription(), new OpenItems(), new OpenPokemon()));
	}

	// Runs when the base command is run with no subcommands.
	@Override
	public int run(CommandContext<CommandSourceStack> context) {

		if (!context.getSource().isPlayer()) {
			context.getSource().sendSystemMessage(Component.literal("This command must be ran by a player."));
			return 1;
		}

		ServerPlayer sender = context.getSource().getPlayer();

		if (Gts.config.isEnableAsyncSearches()) {
			runAsync(sender);
		} else {
			runSync(sender);
		}

		return 1;
	}

	public void runSync(ServerPlayer player) {

		try {
			Page page = new AllListings().getPage(FilterType.ALL);

			UIManager.openUIForcefully(player, page);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.sendMsg(player, Gts.language.getSomethingWentWrong());
		}
	}

	public void runAsync(ServerPlayer player) {

		Utils.sendMsg(player, Gts.language.getLoadingListings());

		ASYNC_EXEC.submit(() -> {
			try {
				Page page = new AllListings().getPage(FilterType.ALL);

				Gts.server.execute(() -> {
					UIManager.openUIForcefully(player, page);
				});
			} catch (Exception e) {
				e.printStackTrace();
				Utils.sendMsg(player, Gts.language.getSomethingWentWrong());
			}
		});
	}
}
