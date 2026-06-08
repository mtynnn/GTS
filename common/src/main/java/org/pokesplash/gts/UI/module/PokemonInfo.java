package org.pokesplash.gts.UI.module;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.PokemonListing;
import org.pokesplash.gts.util.ColorUtil;
import org.pokesplash.gts.util.Utils;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PokemonInfo {

    private static int iv(Pokemon p, Stats s) {
        Integer v = p.getIvs().get(s);
        return v == null ? 0 : v;
    }

    private static int ev(Pokemon p, Stats s) {
        Integer v = p.getEvs().get(s);
        return v == null ? 0 : v;
    }

    private static int totalIvs(Pokemon p) {
        return iv(p, Stats.HP) + iv(p, Stats.ATTACK) + iv(p, Stats.DEFENCE)
             + iv(p, Stats.SPECIAL_ATTACK) + iv(p, Stats.SPECIAL_DEFENCE) + iv(p, Stats.SPEED);
    }

    private static String gender(Pokemon p) {
        return switch (p.getGender()) {
            case MALE   -> Gts.language.getPokemonLoreGenderMale();
            case FEMALE -> Gts.language.getPokemonLoreGenderFemale();
            default     -> "";
        };
    }

    private static String statLine(String template, int ivVal, int evVal) {
        return template
                .replace("{iv}", String.valueOf(ivVal))
                .replace("{ev}", String.valueOf(evVal));
    }

    /**
     * Lore resumido para la lista (AllListings, PokemonListings, etc).
     */
    public static Collection<Component> parseShort(Pokemon pokemon) {
        Collection<Component> lore = new ArrayList<>();

        String genderStr = gender(pokemon);
        String shiny = pokemon.getShiny() ? "  " + Gts.language.getPokemonLoreShiny() : "";

        lore.add(ColorUtil.parse("&#00DDFF✦ &f" + pokemon.getSpecies().getName()
                + "  &7Lv." + pokemon.getLevel()
                + (genderStr.isEmpty() ? "" : "  " + genderStr)
                + shiny));

        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreNature()
                .replace("{value}", pokemon.getNature().getDisplayName())));

        String haStr = Utils.isHA(pokemon) ? Gts.language.getPokemonLoreHA() : "";
        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreAbility()
                .replace("{value}", pokemon.getAbility().getDisplayName() + haStr)));

        int total = totalIvs(pokemon);
        String ivs = iv(pokemon, Stats.HP) + "&8/&f"
                + iv(pokemon, Stats.ATTACK) + "&8/&f"
                + iv(pokemon, Stats.DEFENCE) + "&8/&f"
                + iv(pokemon, Stats.SPECIAL_ATTACK) + "&8/&f"
                + iv(pokemon, Stats.SPECIAL_DEFENCE) + "&8/&f"
                + iv(pokemon, Stats.SPEED);
        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreIvsLine()
                .replace("{ivs}", ivs)
                .replace("{total}", String.valueOf(total))));

        return lore;
    }

    public static Collection<Component> parseShort(PokemonListing listing) {
        return parseShort(listing.getListing());
    }

    /**
     * Lore completo para el detalle (SingleListing, ExpiredListing).
     */
    public static Collection<Component> parse(Pokemon pokemon) {
        Collection<Component> lore = new ArrayList<>();

        // === INFO GENERAL ===
        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreInfoHeader()));

        String types = "";
        for (ElementalType t : pokemon.getSpecies().getTypes()) {
            types += t.getDisplayName().getString() + " ";
        }
        lore.add(ColorUtil.parse(
                Gts.language.getPokemonLoreSpecies().replace("{value}", pokemon.getSpecies().getName())
                + Gts.language.getPokemonLoreType().replace("{value}", types.trim())));

        String haStr = Utils.isHA(pokemon) ? Gts.language.getPokemonLoreHA() : "";
        lore.add(ColorUtil.parse(
                Gts.language.getPokemonLoreNature().replace("{value}", pokemon.getNature().getDisplayName())
                + Gts.language.getPokemonLoreAbility().replace("{value}", pokemon.getAbility().getDisplayName() + haStr)));

        Item ball = pokemon.getCaughtBall().item();
        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreBall()
                .replace("{value}", ball.getName(new ItemStack(ball)).getString())));

        if (pokemon.getShiny()) {
            lore.add(ColorUtil.parse(Gts.language.getPokemonLoreShiny()));
        }

        String genderStr = gender(pokemon);
        if (!genderStr.isEmpty()) {
            lore.add(ColorUtil.parse(" &7Género: " + genderStr));
        }

        if (!pokemon.getPersistentData().getString("size").isEmpty()) {
            lore.add(ColorUtil.parse(Gts.language.getPokemonLoreSize()
                    .replace("{value}", Utils.capitaliseFirst(pokemon.getPersistentData().getString("size")))));
        }

        if (pokemon.getPersistentData().getBoolean("pokerus")) {
            lore.add(ColorUtil.parse(Gts.language.getPokemonLorePokerus()));
        }

        // === ESTADÍSTICAS ===
        lore.add(Component.empty());
        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreStatsHeader()));
        lore.add(ColorUtil.parse(statLine(Gts.language.getPokemonLoreHP(),    iv(pokemon, Stats.HP),              ev(pokemon, Stats.HP))));
        lore.add(ColorUtil.parse(statLine(Gts.language.getPokemonLoreAtk(),   iv(pokemon, Stats.ATTACK),          ev(pokemon, Stats.ATTACK))));
        lore.add(ColorUtil.parse(statLine(Gts.language.getPokemonLoreDef(),   iv(pokemon, Stats.DEFENCE),         ev(pokemon, Stats.DEFENCE))));
        lore.add(ColorUtil.parse(statLine(Gts.language.getPokemonLoreSpAtk(), iv(pokemon, Stats.SPECIAL_ATTACK),  ev(pokemon, Stats.SPECIAL_ATTACK))));
        lore.add(ColorUtil.parse(statLine(Gts.language.getPokemonLoreSpDef(), iv(pokemon, Stats.SPECIAL_DEFENCE), ev(pokemon, Stats.SPECIAL_DEFENCE))));
        lore.add(ColorUtil.parse(statLine(Gts.language.getPokemonLoreSpeed(), iv(pokemon, Stats.SPEED),           ev(pokemon, Stats.SPEED))));

        // === MOVIMIENTOS ===
        lore.add(Component.empty());
        lore.add(ColorUtil.parse(Gts.language.getPokemonLoreMovesHeader()));
        for (Move move : pokemon.getMoveSet().getMoves()) {
            lore.add(ColorUtil.parse(" &f" + move.getTemplate().getDisplayName().getString()));
        }

        // === BREEDABLE ===
        if (Gts.config.isShowBreedable()) {
            lore.add(Component.empty());
            if (pokemon.getPersistentData().contains("breedable") &&
                    !pokemon.getPersistentData().getBoolean("breedable")) {
                lore.add(ColorUtil.parse(Gts.language.getPokemonLoreUnbreedable()));
            } else {
                lore.add(ColorUtil.parse(Gts.language.getPokemonLoreBreedable()));
            }
        }

        return lore;
    }

    public static Collection<Component> parse(PokemonListing listing) {
        return parse(listing.getListing());
    }
}
