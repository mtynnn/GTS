package org.pokesplash.gts.config;

import com.cobblemon.mod.common.CobblemonItems;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.util.CodecUtils;
import org.pokesplash.gts.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Lang {
	// placeholders
	// {listing}
	// {seller}
	// {buyer}
	// {max_listings}
	// {min_price}
	// {max_price}

    private int version;

	/**
	 * Titles
	 */
	private String title;
	private String itemListingsTitle;
	private String pokemonListingsTitle;
	private String expiredListingsTitle;
	private String pokemonTitle;
	private String itemTitle;
	private String filteredListingsTitle;
	private String historyTitle;
	private String manageTitle;


	/**
	 * Prefix
	 */
	private String prefix;

	/**
	 * Messages
	 */
	private String purchaseMessageBuyer;
	private String cancelListing;
	private String returnListingSuccess;
	private String returnListingFail;
	private String maximumListings;
	private String minimumListingPrice;
	private String maximumListingPrice;
	private String listingSuccess;
	private String listingFail;
	private String noPokemonInSlot;
	private String noItemInHand;
	private String bannedItem;
	private String bannedPokemon;
	private String insufficientItems;
	private String itemIdNotFound;
	private String zeroItemAmount;
	private String reloadMessage;
	private String insufficientFunds;
	private String listingBought;
	private String newListingBroadcast;
	private String insufficientInventorySpace;
	private String onlyOnePokemonInParty;
	private String playerOnly;
	private String somethingWentWrong;
	private String loadingListings;
	private String notTradeable;
	private String inBattle;
	private String timedOut;
	private String stackSizeError;
	private String averagePrice;
	private String noSalesHistory;
	private String mustHoldItem;
	private String itemDescriptionMessage;
	private String debugModeSet;
	private String timeoutRemoved;
	private String timeoutSet;
	private String playerNotFound;
	private String saveItemSuccess;
	private String saveItemFail;

	/**
	 * Button Labels
	 */
	private String confirmPurchaseButtonLabel;
	private String cancelPurchaseButtonLabel;
	private String removeListingButtonLabel;
	private String itemListingsButtonLabel;
	private String pokemonListingsButtonLabel;
	private String manageListingsButtonLabel;
	private String nextPageButtonLabel;
	private String previousPageButtonLabel;
	private String sortByPriceButtonLabel;
	private String sortByNewestButtonLabel;
	private String sortByPokemonButtonLabel;
	private String sortByNameButtonLabel;
	private String receiveListingButtonLabel;
	private String relistExpiredButtonLabel;
	private String expiredListingButtonLabel;
	private String pokemonBall;
	private String informationButtonLabel;
	private String refreshButtonLabel;
	private String filterButtonLabel;
	private String backButtonLabel;

	/**
	 * Button Lores
	 */
	private List<String> nextPageButtonLore;
	private List<String> previousPageButtonLore;
	private List<String> manageListingsButtonLore;
	private List<String> expiredListingsButtonLore;
	private List<String> relistButtonLore;
	private List<String> filterButtonLoreAll;
	private List<String> filterButtonLorePokemon;
	private List<String> filterButtonLoreItems;
	private List<String> sortButtonLorePrice;
	private List<String> sortButtonLoreDate;
	private List<String> sortButtonLoreName;
	private List<String> purchaseButtonLore;
	private List<String> cancelButtonLore;
	private List<String> removeListingButtonLore;
	private List<String> receiveListingButtonLore;
	private List<String> informationButtonLore;
	private List<String> refreshButtonLore;
	private List<String> backButtonLore;
	private List<String> seePokemonButtonLore;
	private List<String> seeItemsButtonLore;

	/**
	 * Button Materials
	 */
	private JsonElement itemListingsButtonItem;
	private JsonElement pokemonListingsButtonItem;
	private JsonElement manageListingsButtonItem;
	private JsonElement expiredListingsButtonItem;
	private JsonElement nextPageButtonItems;
	private JsonElement previousPageButtonItems;
	private JsonElement fillerItem;
	private JsonElement purchaseButtonItem;
	private JsonElement cancelButtonItem;
	private JsonElement removeListingButtonItem;
	private JsonElement sortByPriceButtonItem;
	private JsonElement sortByNewestButtonItem;
	private JsonElement sortByNameButtonItem;
	private JsonElement relistExpiredButtonItem;
	private JsonElement informationButtonItem;
	private JsonElement refreshButtonItem;
	private JsonElement filterAllButtonItem;
	private JsonElement filterPokemonButtonItem;
	private JsonElement filterItemsButtonItem;
	private JsonElement backButtonItem;

	/**
	 * Pokemon lore labels
	 */
	private String pokemonLoreInfoHeader;
	private String pokemonLoreSpecies;
	private String pokemonLoreType;
	private String pokemonLoreNature;
	private String pokemonLoreAbility;
	private String pokemonLoreHA;
	private String pokemonLoreBall;
	private String pokemonLoreShiny;
	private String pokemonLoreGenderMale;
	private String pokemonLoreGenderFemale;
	private String pokemonLoreSize;
	private String pokemonLorePokerus;
	private String pokemonLoreStatsHeader;
	private String pokemonLoreHP;
	private String pokemonLoreAtk;
	private String pokemonLoreDef;
	private String pokemonLoreSpAtk;
	private String pokemonLoreSpDef;
	private String pokemonLoreSpeed;
	private String pokemonLoreMovesHeader;
	private String pokemonLoreBreedable;
	private String pokemonLoreUnbreedable;
	private String pokemonLoreIvsLine;

	/**
	 * Placeholders
	 */
	private String seller;
	private String price;
	private String remainingTime;
	private String sold_date;
	private String buyer;


	/**
	 * Constructor to generate a file if one doesn't exist.
	 */
	public Lang() {
        version = Gts.LANG_FILE_VERSION;
		prefix = "&8[&#00DDFFɢᴛs&8] &r";
		title = "§0ɢᴛs";
		expiredListingsTitle = "§0ɪᴛᴇᴍs ᴇxᴘɪʀᴀᴅᴏs";
		itemTitle = "§0ɢᴛs · ɪᴛᴇᴍ";
		pokemonTitle = "§0ɢᴛs · ᴘᴏᴋᴇᴍᴏɴ";
		filteredListingsTitle = "§0ʙúsǫᴜᴇᴅᴀ: %search%";
		historyTitle = "§0ʜɪsᴛᴏʀɪᴀʟ";
		itemListingsTitle = "§0ɢᴛs · ɪᴛᴇᴍs";
		pokemonListingsTitle = "§0ɢᴛs · ᴘᴏᴋᴇᴍᴏɴ";
		manageTitle = "§0ᴍɪs ʟɪsᴛɪɴɢs";
		purchaseMessageBuyer = "&#33FF33¡Compraste {listing} de {seller}!";
		relistExpiredButtonLabel = "&#00DDFF ʀᴇᴘᴜʙʟɪᴄᴀʀ ᴛᴏᴅᴏ";
		relistExpiredButtonItem = CodecUtils.encodeItem(new ItemStack(CobblemonItems.RARE_CANDY));
		cancelListing = "&#FF9900¡El listing de {listing} fue cancelado!";
		returnListingSuccess = "&#33FF33¡Recibiste el listing de {listing}!";
		returnListingFail = "&#FF3333No se pudo recibir el listing de {listing}.";
		maximumListings = "&#FF3333Solo puedes tener {max_listings} listings activos.";
		minimumListingPrice = "&#FF3333Tu listing debe superar el precio mínimo de {min_price}";
		maximumListingPrice = "&#FF3333Tu listing debe estar por debajo del precio máximo de {max_price}";
		listingSuccess = "&#33FF33¡Agregaste {listing} al GTS exitosamente!";
		listingFail = "&#FF3333No se pudo agregar {listing} al GTS.";
		noPokemonInSlot = "&#FF3333No hay ningún Pokémon en el slot indicado.";
		noItemInHand = "&#FF3333No tienes ningún item en la mano.";
		bannedItem = "&#FF3333{listing} está prohibido en el GTS.";
		bannedPokemon = "&#FF3333{listing} está prohibido en el GTS.";
		insufficientItems = "&#FF3333No tienes suficientes {listing} para listar.";
		itemIdNotFound = "&#FF3333No se encontró el item.";
		zeroItemAmount = "&#FF3333La cantidad no puede ser cero.";
		reloadMessage = "&#33FF33¡Configuración recargada!";
		insufficientFunds = "&#FF3333No tienes suficiente dinero para comprar este listing.";
		listingBought = "&#33FF33Tu {listing} fue comprado por {buyer}";
		playerOnly = "&#FF3333Este comando solo puede ser ejecutado por un jugador.";
		somethingWentWrong = "&#FF3333Algo salió mal, avisa a un administrador.";
		loadingListings = "&#00DDFFCargando listings del GTS...";
		notTradeable = "&#FF3333Este Pokémon no se puede intercambiar.";
		inBattle = "&#FF3333No puedes publicar en el GTS mientras estás en batalla.";
		timedOut = "&#FF3333Estás en timeout por {time}.";
		stackSizeError = "&#FF3333El tamaño del stack no puede dividir la cantidad indicada.";
		averagePrice = "&#00DDFFPrecio promedio: &#AAFFFF${price}";
		noSalesHistory = "&#FF3333Este item/Pokémon no tiene historial de ventas.";
		mustHoldItem = "&#FF3333Debes tener un item en la mano.";
		itemDescriptionMessage = "&#00DDFFDescripción del item: &#AAFFFF{item}";
		debugModeSet = "&#00DDFFModo debug: &#AAFFFF{value}";
		timeoutRemoved = "&#33FF33Timeout eliminado para {player}.";
		timeoutSet = "&#00DDFF{player} &#AAFFAAha recibido un timeout de &#AAFFFF{time}.";
		playerNotFound = "&#FF3333No se encontró al jugador: &#FFAAAA{player}";
		saveItemSuccess = "&#33FF33¡Item guardado exitosamente!";
		saveItemFail = "&#FF3333No se pudo guardar el item.";
		itemListingsButtonItem = CodecUtils.encodeItem(new ItemStack(CobblemonItems.ASSAULT_VEST));
		pokemonListingsButtonItem = CodecUtils.encodeItem(new ItemStack(CobblemonItems.POKE_BALL));
		manageListingsButtonItem = CodecUtils.encodeItem(new ItemStack(Items.ENDER_CHEST));
		nextPageButtonItems = CodecUtils.encodeItem(new ItemStack(Items.ARROW));
		previousPageButtonItems = CodecUtils.encodeItem(new ItemStack(Items.ARROW));
		fillerItem = CodecUtils.encodeItem(new ItemStack(Items.GRAY_STAINED_GLASS_PANE));
		purchaseButtonItem = CodecUtils.encodeItem(new ItemStack(Items.LIME_STAINED_GLASS_PANE));
		cancelButtonItem = CodecUtils.encodeItem(new ItemStack(Items.RED_STAINED_GLASS_PANE));
		sortByPriceButtonItem = CodecUtils.encodeItem(new ItemStack(Items.GLOWSTONE_DUST));
		sortByNewestButtonItem = CodecUtils.encodeItem(new ItemStack(Items.REDSTONE));
		sortByNameButtonItem = CodecUtils.encodeItem(new ItemStack(Items.GLOWSTONE_DUST));
		expiredListingsButtonItem = CodecUtils.encodeItem(new ItemStack(CobblemonItems.LINK_CABLE));
		removeListingButtonItem = CodecUtils.encodeItem(new ItemStack(Items.RED_STAINED_GLASS_PANE));
		informationButtonItem = CodecUtils.encodeItem(new ItemStack(Items.BOOK));
		refreshButtonItem = CodecUtils.encodeItem(new ItemStack(Items.SUNFLOWER));
		filterAllButtonItem = CodecUtils.encodeItem(new ItemStack(Items.GLOWSTONE_DUST));
		filterPokemonButtonItem = CodecUtils.encodeItem(new ItemStack(Items.GLOWSTONE_DUST));
		filterItemsButtonItem = CodecUtils.encodeItem(new ItemStack(Items.GLOWSTONE_DUST));
		backButtonItem = CodecUtils.encodeItem(new ItemStack(Items.ARROW));
		newListingBroadcast = "&#FFCC00{seller} &#AAFFAAacaba de agregar &#FFCC00{listing} &#AAFFAAal GTS.";
		seller = " &c🪓 &fVendedor: &c";
		price = " &6⛃ &fPrecio: &6";
		sold_date = " &a⌚ &fFecha de venta: &a";
		buyer = " &2🔥 &fComprador: &2";
		remainingTime = " &a⌚ &fExpira: &a";

		confirmPurchaseButtonLabel = "&#33FF33ᴄᴏɴғɪʀᴍᴀʀ ᴄᴏᴍᴘʀᴀ";
		cancelPurchaseButtonLabel = "&#FF3333ᴄᴀɴᴄᴇʟᴀʀ";
		removeListingButtonLabel = "&#FF3333ᴇʟɪᴍɪɴᴀʀ ʟɪsᴛɪɴɢ";
		itemListingsButtonLabel = "&#00DDFF ɪᴛᴇᴍs";
		pokemonListingsButtonLabel = "&#00DDFF ᴘᴏᴋᴇᴍᴏɴ";
		manageListingsButtonLabel = "&#00DDFF ᴍɪs ɪᴛᴇᴍs";
		nextPageButtonLabel = "&#00DDFF sɪɢᴜɪᴇɴᴛᴇ ᴘᴀɢɪɴᴀ";
		previousPageButtonLabel = "&#00DDFF ᴘᴀɢɪɴᴀ ᴀɴᴛᴇʀɪᴏʀ";
		sortByPriceButtonLabel = "&#00DDFF ᴏʀᴅᴇɴᴀᴍɪᴇɴᴛᴏ";
		sortByNewestButtonLabel = "&#00DDFF ᴏʀᴅᴇɴᴀᴍɪᴇɴᴛᴏ";
		sortByPokemonButtonLabel = "&#00DDFF ᴏʀᴅᴇɴᴀᴍɪᴇɴᴛᴏ";
		sortByNameButtonLabel = "&#00DDFF ᴏʀᴅᴇɴᴀᴍɪᴇɴᴛᴏ";
		receiveListingButtonLabel = "&#33FF33 ʀᴇᴄɪʙɪʀ ɪᴛᴇᴍ";
		expiredListingButtonLabel = "&#00DDFF ᴇxᴘɪʀᴀᴅᴏs";
		pokemonBall = "&#33FF33Pokéball: ";
		informationButtonLabel = "&#00DDFF ɪɴғᴏʀᴍᴀᴄɪóɴ";
		refreshButtonLabel = "&#00DDFF ᴀᴄᴛᴜᴀʟɪᴢᴀʀ";
		filterButtonLabel = "&#00DDFF ғɪʟᴛʀᴏ";
		backButtonLabel = "&#00DDFF ᴠᴏʟᴠᴇʀ";

		// Button lores
		nextPageButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para ir",
			" &fa la siguiente página!",
			" ",
			"&#00DDFF→ Click para siguiente página"
		);
		previousPageButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para volver",
			" &fa la página anterior!",
			" ",
			"&#00DDFF→ Click para página anterior"
		);
		manageListingsButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para ir",
			" &fa la página de mis items!",
			" ",
			"&#00DDFF→ Click para mis items"
		);
		expiredListingsButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para ver",
			" &ftus items expirados!",
			" ",
			"&#00DDFF→ Click para items expirados"
		);
		relistButtonLore = Arrays.asList(
			" ",
			" &fRe-publica todos tus",
			" &fitems expirados!",
			" ",
			"&#00DDFF→ Click para re-publicar todo"
		);
		filterButtonLoreAll = Arrays.asList(
			" ",
			" &#00DDFFSeleccionado actualmente:",
			"  &#444444⏺ &#AAFFAAᴛᴏᴅᴏ",
			"  &#444444⏺ &#BBBBBBsᴏʟᴏ ᴘᴏᴋᴇᴍᴏɴ",
			"  &#444444⏺ &#BBBBBBsᴏʟᴏ ɪᴛᴇᴍs",
			" ",
			"&#00DDFF→ Click para cambiar filtro"
		);
		filterButtonLorePokemon = Arrays.asList(
			" ",
			" &#00DDFFSeleccionado actualmente:",
			"  &#444444⏺ &#BBBBBBᴛᴏᴅᴏ",
			"  &#444444⏺ &#AAFFAAsᴏʟᴏ ᴘᴏᴋᴇᴍᴏɴ",
			"  &#444444⏺ &#BBBBBBsᴏʟᴏ ɪᴛᴇᴍs",
			" ",
			"&#00DDFF→ Click para cambiar filtro"
		);
		filterButtonLoreItems = Arrays.asList(
			" ",
			" &#00DDFFSeleccionado actualmente:",
			"  &#444444⏺ &#BBBBBBᴛᴏᴅᴏ",
			"  &#444444⏺ &#BBBBBBsᴏʟᴏ ᴘᴏᴋᴇᴍᴏɴ",
			"  &#444444⏺ &#AAFFAAsᴏʟᴏ ɪᴛᴇᴍs",
			" ",
			"&#00DDFF→ Click para cambiar filtro"
		);
		sortButtonLorePrice = Arrays.asList(
			" ",
			" &#00DDFFSeleccionado actualmente:",
			"  &#444444⏺ &#BBBBBBᴍás ʀᴇᴄɪᴇɴᴛᴇ",
			"  &#444444⏺ &#AAFFAAᴍᴇɴᴏʀ ᴘʀᴇᴄɪᴏ",
			"  &#444444⏺ &#BBBBBBɴᴏᴍʙʀᴇ",
			" ",
			"&#00DDFF→ Click para cambiar ordenamiento"
		);
		sortButtonLoreDate = Arrays.asList(
			" ",
			" &#00DDFFSeleccionado actualmente:",
			"  &#444444⏺ &#AAFFAAᴍás ʀᴇᴄɪᴇɴᴛᴇ",
			"  &#444444⏺ &#BBBBBBᴍᴇɴᴏʀ ᴘʀᴇᴄɪᴏ",
			"  &#444444⏺ &#BBBBBBɴᴏᴍʙʀᴇ",
			" ",
			"&#00DDFF→ Click para cambiar ordenamiento"
		);
		sortButtonLoreName = Arrays.asList(
			" ",
			" &#00DDFFSeleccionado actualmente:",
			"  &#444444⏺ &#BBBBBBᴍás ʀᴇᴄɪᴇɴᴛᴇ",
			"  &#444444⏺ &#BBBBBBᴍᴇɴᴏʀ ᴘʀᴇᴄɪᴏ",
			"  &#444444⏺ &#AAFFAAɴᴏᴍʙʀᴇ",
			" ",
			"&#00DDFF→ Click para cambiar ordenamiento"
		);
		purchaseButtonLore = Arrays.asList(
			" ",
			" &f¿Estás seguro de que quieres comprar?",
			" &f¡Esta acción es irreversible!",
			" ",
			"&#33FF33→ Click para confirmar compra"
		);
		cancelButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para volver",
			" &fal menú principal!",
			" ",
			"&#FF3333→ Click para cancelar"
		);
		removeListingButtonLore = Arrays.asList(
			" ",
			" &fElimina este listing del GTS.",
			" ",
			"&#FF3333→ Click para eliminar listing"
		);
		receiveListingButtonLore = Arrays.asList(
			" ",
			" &fRecupera este item",
			" &fde tus expirados!",
			" ",
			"&#33FF33→ Click para recibir item"
		);
		informationButtonLore = Arrays.asList(
			" ",
			" &f¡En este menú puedes",
			" &fvender o comprar items!",
			" ",
			"&#00DDFF→ Vender: &#AAFFFF/gts sell"
		);
		refreshButtonLore = Arrays.asList(
			" ",
			" &fActualiza la lista",
			" &fde listings!",
			" ",
			"&#00DDFF→ Click para actualizar"
		);
		backButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para volver",
			" &fal menú principal!",
			" ",
			"&#00DDFF→ Click para volver"
		);
		seePokemonButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para ver",
			" &fsolo listings de Pokémon!",
			" ",
			"&#00DDFF→ Click para ver Pokémon"
		);
		seeItemsButtonLore = Arrays.asList(
			" ",
			" &fHaz click aquí para ver",
			" &fsolo listings de items!",
			" ",
			"&#00DDFF→ Click para ver items"
		);

		// Pokemon lore labels
		pokemonLoreInfoHeader   = "&#00DDFF✦ ɪɴғᴏ ɢᴇɴᴇʀᴀʟ";
		pokemonLoreSpecies      = " &7Especie: &f{value}";
		pokemonLoreType         = "   &7Tipo: &f{value}";
		pokemonLoreNature       = " &7Naturaleza: &f{value}";
		pokemonLoreAbility      = "   &7Habilidad: &f{value}";
		pokemonLoreHA           = " &b(OH)";
		pokemonLoreBall         = " &7Pokéball: &f{value}";
		pokemonLoreShiny        = " &#FFD700★ &fShiny";
		pokemonLoreGenderMale   = "&9♂";
		pokemonLoreGenderFemale = "&d♀";
		pokemonLoreSize         = " &7Tamaño: &f{value}";
		pokemonLorePokerus      = " &7Pokérus: &aSí";
		pokemonLoreStatsHeader  = "&#00DDFF◈ ᴇsᴛᴀᴅísᴛɪᴄᴀs  &8(IV / EV)";
		pokemonLoreHP           = " &7PS      &f{iv} &8/ &7{ev}";
		pokemonLoreAtk          = " &cAtq    &f{iv} &8/ &7{ev}";
		pokemonLoreDef          = " &aDef    &f{iv} &8/ &7{ev}";
		pokemonLoreSpAtk        = " &9Sp.Atq &f{iv} &8/ &7{ev}";
		pokemonLoreSpDef        = " &3Sp.Def &f{iv} &8/ &7{ev}";
		pokemonLoreSpeed        = " &bVel    &f{iv} &8/ &7{ev}";
		pokemonLoreMovesHeader  = "&#00DDFF⚔ ᴍᴏᴠɪᴍɪᴇɴᴛᴏs";
		pokemonLoreBreedable    = "&bReproducible";
		pokemonLoreUnbreedable  = "&cNo reproducible";
		pokemonLoreIvsLine      = "&#00DDFF◈ &7IVs: &f{ivs}  &8(Total: {total})";
	}



    /**
	 * Bunch of getters for the fields.
	 */
    public int getVersion() {
        return version;
    }
	public String getPurchaseMessageBuyer() {
		return purchaseMessageBuyer;
	}
	public String getTitle() {
		return title;
	}
	public String getCancelListing() {
		return cancelListing;
	}
	public String getReturnListingSuccess() {
		return returnListingSuccess;
	}
	public String getReturnListingFail() {
		return returnListingFail;
	}
	public String getMaximumListings() {
		return maximumListings;
	}
	public String getMinimumListingPrice() {
		return minimumListingPrice;
	}
	public String getMaximumListingPrice() {
		return maximumListingPrice;
	}
	public String getListingSuccess() {
		return listingSuccess;
	}
	public String getListingFail() {
		return listingFail;
	}
	public String getNoPokemonInSlot() {
		return noPokemonInSlot;
	}
	public String getNoItemInHand() {
		return noItemInHand;
	}
	public String getBannedItem() {
		return bannedItem;
	}
	public String getInsufficientItems() {
		return insufficientItems;
	}
	public String getItemIdNotFound() {
		return itemIdNotFound;
	}
	public String getZeroItemAmount() {
		return zeroItemAmount;
	}
	public String getReloadMessage() {
		return reloadMessage;
	}
	public String getInsufficientFunds() {
		return insufficientFunds;
	}
	public String getListingBought() {
		return listingBought;
	}
	public ItemStack getItemListingsButtonItem() {
		return CodecUtils.decodeItem(itemListingsButtonItem);
	}
	public ItemStack getPokemonListingsButtonItem() {
		return CodecUtils.decodeItem(pokemonListingsButtonItem);
	}
	public ItemStack getManageListingsButtonItem() {
		return CodecUtils.decodeItem(manageListingsButtonItem);
	}
	public ItemStack getNextPageButtonItems() {
		return CodecUtils.decodeItem(nextPageButtonItems);
	}
	public ItemStack getPreviousPageButtonItems() {
		return CodecUtils.decodeItem(previousPageButtonItems);
	}
	public ItemStack getFillerItem() {
		return CodecUtils.decodeItem(fillerItem);
	}
	public ItemStack getPurchaseButtonItem() {
		return CodecUtils.decodeItem(purchaseButtonItem);
	}
	public ItemStack getCancelButtonItem() {
		return CodecUtils.decodeItem(cancelButtonItem);
	}
	public ItemStack getSortByPriceButtonItem() {
		return CodecUtils.decodeItem(sortByPriceButtonItem);
	}
	public ItemStack getSortByNewestButtonItem() {
		return CodecUtils.decodeItem(sortByNewestButtonItem);
	}
	public ItemStack getSortByNameButtonItem() {
		return CodecUtils.decodeItem(sortByNameButtonItem);
	}
	public ItemStack getExpiredListingsButtonItem() {
		return CodecUtils.decodeItem(expiredListingsButtonItem);
	}
	public ItemStack getRemoveListingButtonItem() {
		return CodecUtils.decodeItem(removeListingButtonItem);
	}
	public ItemStack getRelistExpiredButtonItem() {
		return CodecUtils.decodeItem(relistExpiredButtonItem);
	}
	public ItemStack getInformationButtonItem() {
		return CodecUtils.decodeItem(informationButtonItem);
	}
	public ItemStack getRefreshButtonItem() {
		return CodecUtils.decodeItem(refreshButtonItem);
	}
	public ItemStack getFilterAllButtonItem() {
		return CodecUtils.decodeItem(filterAllButtonItem);
	}
	public ItemStack getFilterPokemonButtonItem() {
		return CodecUtils.decodeItem(filterPokemonButtonItem);
	}
	public ItemStack getFilterItemsButtonItem() {
		return CodecUtils.decodeItem(filterItemsButtonItem);
	}
	public ItemStack getBackButtonItem() {
		return CodecUtils.decodeItem(backButtonItem);
	}
	public String getNewListingBroadcast() {
		return newListingBroadcast;
	}
	public String getInsufficientInventorySpace() { return insufficientInventorySpace; }
	public String getSeller() {
		return seller;
	}
	public String getPrice() {
		return price;
	}
	public String getRemainingTime() {
		return remainingTime;
	}
	public String getConfirmPurchaseButtonLabel() {
		return confirmPurchaseButtonLabel;
	}
	public String getCancelPurchaseButtonLabel() {
		return cancelPurchaseButtonLabel;
	}
	public String getRemoveListingButtonLabel() {
		return removeListingButtonLabel;
	}
	public String getItemListingsButtonLabel() {
		return itemListingsButtonLabel;
	}
	public String getPokemonListingsButtonLabel() {
		return pokemonListingsButtonLabel;
	}
	public String getManageListingsButtonLabel() {
		return manageListingsButtonLabel;
	}
	public String getNextPageButtonLabel() {
		return nextPageButtonLabel;
	}
	public String getPreviousPageButtonLabel() {
		return previousPageButtonLabel;
	}
	public String getSortByPriceButtonLabel() {
		return sortByPriceButtonLabel;
	}
	public String getSortByNewestButtonLabel() {
		return sortByNewestButtonLabel;
	}
	public String getSortByPokemonButtonLabel() {
		return sortByPokemonButtonLabel;
	}
	public String getSortByNameButtonLabel() {
		return sortByNameButtonLabel;
	}
	public String getReceiveListingButtonLabel() {
		return receiveListingButtonLabel;
	}
	public String getSold_date() {
		return sold_date;
	}
	public String getBuyer() {
		return buyer;
	}
	public String getBannedPokemon() {
		return bannedPokemon;
	}
	public String getRelistExpiredButtonLabel() {
		return relistExpiredButtonLabel;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public String getExpiredListingsTitle() {
		return expiredListingsTitle;
	}
	public String getPokemonTitle() {
		return pokemonTitle;
	}
	public String getFilteredListingsTitle() {
		return filteredListingsTitle;
	}
	public String getHistoryTitle() {
		return historyTitle;
	}
	public String getItemListingsTitle() {
		return itemListingsTitle;
	}
	public String getManageTitle() {
		return manageTitle;
	}
	public String getPokemonListingsTitle() {
		return pokemonListingsTitle;
	}
	public String getOnlyOnePokemonInParty() { return onlyOnePokemonInParty; }
	public String getExpiredListingButtonLabel() {
		return expiredListingButtonLabel;
	}
	public String getPokemonBall() {
		return pokemonBall;
	}
	public String getInformationButtonLabel() {
		return informationButtonLabel;
	}
	public String getRefreshButtonLabel() {
		return refreshButtonLabel;
	}
	public String getFilterButtonLabel() {
		return filterButtonLabel;
	}
	public String getBackButtonLabel() {
		return backButtonLabel;
	}
	public List<String> getNextPageButtonLore() {
		return nextPageButtonLore;
	}
	public List<String> getPreviousPageButtonLore() {
		return previousPageButtonLore;
	}
	public List<String> getManageListingsButtonLore() {
		return manageListingsButtonLore;
	}
	public List<String> getExpiredListingsButtonLore() {
		return expiredListingsButtonLore;
	}
	public List<String> getRelistButtonLore() {
		return relistButtonLore;
	}
	public List<String> getFilterButtonLoreAll() {
		return filterButtonLoreAll;
	}
	public List<String> getFilterButtonLorePokemon() {
		return filterButtonLorePokemon;
	}
	public List<String> getFilterButtonLoreItems() {
		return filterButtonLoreItems;
	}
	public List<String> getSortButtonLorePrice() {
		return sortButtonLorePrice;
	}
	public List<String> getSortButtonLoreDate() {
		return sortButtonLoreDate;
	}
	public List<String> getSortButtonLoreName() {
		return sortButtonLoreName;
	}
	public List<String> getPurchaseButtonLore() {
		return purchaseButtonLore;
	}
	public List<String> getCancelButtonLore() {
		return cancelButtonLore;
	}
	public List<String> getRemoveListingButtonLore() {
		return removeListingButtonLore;
	}
	public List<String> getReceiveListingButtonLore() {
		return receiveListingButtonLore;
	}
	public List<String> getInformationButtonLore() {
		return informationButtonLore;
	}
	public List<String> getRefreshButtonLore() {
		return refreshButtonLore;
	}
	public List<String> getBackButtonLore() {
		return backButtonLore;
	}
	public List<String> getSeePokemonButtonLore() {
		return seePokemonButtonLore;
	}
	public List<String> getSeeItemsButtonLore() {
		return seeItemsButtonLore;
	}

	public String getPrefix() { return prefix; }
	public String getPlayerOnly() { return playerOnly; }
	public String getSomethingWentWrong() { return somethingWentWrong; }
	public String getLoadingListings() { return loadingListings; }
	public String getNotTradeable() { return notTradeable; }
	public String getInBattle() { return inBattle; }
	public String getTimedOut() { return timedOut; }
	public String getStackSizeError() { return stackSizeError; }
	public String getAveragePrice() { return averagePrice; }
	public String getNoSalesHistory() { return noSalesHistory; }
	public String getMustHoldItem() { return mustHoldItem; }
	public String getItemDescriptionMessage() { return itemDescriptionMessage; }
	public String getDebugModeSet() { return debugModeSet; }
	public String getTimeoutRemoved() { return timeoutRemoved; }
	public String getTimeoutSet() { return timeoutSet; }
	public String getPlayerNotFound() { return playerNotFound; }
	public String getSaveItemSuccess() { return saveItemSuccess; }
	public String getSaveItemFail() { return saveItemFail; }

	public String getPokemonLoreInfoHeader()   { return pokemonLoreInfoHeader; }
	public String getPokemonLoreSpecies()      { return pokemonLoreSpecies; }
	public String getPokemonLoreType()         { return pokemonLoreType; }
	public String getPokemonLoreNature()       { return pokemonLoreNature; }
	public String getPokemonLoreAbility()      { return pokemonLoreAbility; }
	public String getPokemonLoreHA()           { return pokemonLoreHA; }
	public String getPokemonLoreBall()         { return pokemonLoreBall; }
	public String getPokemonLoreShiny()        { return pokemonLoreShiny; }
	public String getPokemonLoreGenderMale()   { return pokemonLoreGenderMale; }
	public String getPokemonLoreGenderFemale() { return pokemonLoreGenderFemale; }
	public String getPokemonLoreSize()         { return pokemonLoreSize; }
	public String getPokemonLorePokerus()      { return pokemonLorePokerus; }
	public String getPokemonLoreStatsHeader()  { return pokemonLoreStatsHeader; }
	public String getPokemonLoreHP()           { return pokemonLoreHP; }
	public String getPokemonLoreAtk()          { return pokemonLoreAtk; }
	public String getPokemonLoreDef()          { return pokemonLoreDef; }
	public String getPokemonLoreSpAtk()        { return pokemonLoreSpAtk; }
	public String getPokemonLoreSpDef()        { return pokemonLoreSpDef; }
	public String getPokemonLoreSpeed()        { return pokemonLoreSpeed; }
	public String getPokemonLoreMovesHeader()  { return pokemonLoreMovesHeader; }
	public String getPokemonLoreBreedable()    { return pokemonLoreBreedable; }
	public String getPokemonLoreUnbreedable()  { return pokemonLoreUnbreedable; }
	public String getPokemonLoreIvsLine()      { return pokemonLoreIvsLine; }

	public CompletableFuture<Boolean> write() {
		Gson gson = Utils.newGson();
		String data = gson.toJson(this);
		return Utils.writeFileAsync("/config/gts/", "lang.json", data);
	}
}
