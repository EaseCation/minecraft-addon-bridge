package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines this entity's ability to trade with players. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeTable(
    /* Determines when the mob transforms, if the trades should be converted when the new mob has a economy<i>trade</i>table. When the trades are converted, the mob will generate a new trade list with their new trade table, but then it will try to convert any of the same trades over to have the same enchantments and user data. For example, if the original has a Emerald to Enchanted Iron Sword (Sharpness 1), and the new trade also has an Emerald for Enchanted Iron Sword, then the enchantment will be Sharpness 1. */
    @JsonProperty("convert_trades_economy") @Nullable Boolean convertTradesEconomy,
    /* Name to be displayed while trading with this entity. */
    @JsonProperty("display_name") @Nullable String displayName,
    /* Used to determine if trading with entity opens the new trade screen. */
    @JsonProperty("new_screen") @Nullable Boolean newScreen,
    /* Determines if the trades should persist when the mob transforms. This makes it so that the next time the mob is transformed to something with a trade<i>table or economy</i>trade_table, then it keeps their trades. */
    @JsonProperty("persist_trades") @Nullable Boolean persistTrades,
    /* File path relative to the resource pack root for this entity's trades. */
    @JsonProperty("table") @Nullable String table
) {
}
