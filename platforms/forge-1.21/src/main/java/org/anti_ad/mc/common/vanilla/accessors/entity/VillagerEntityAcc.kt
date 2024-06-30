package org.anti_ad.mc.common.vanilla.accessors.entity
/*

import org.anti_ad.mc.common.vanilla.alias.ClickableWidget
import org.anti_ad.mc.common.vanilla.alias.ItemStack
import org.anti_ad.mc.common.vanilla.alias.MerchantScreen
import org.anti_ad.mc.common.vanilla.alias.NbtElement
import org.anti_ad.mc.common.vanilla.alias.StringNbtReader
import org.anti_ad.mc.common.vanilla.alias.entity.VillagerEntity
import org.anti_ad.mc.common.vanilla.alias.village.VillagerProfession
import org.anti_ad.mc.common.vanilla.alias.village.TradeOffer

val VillagerEntity.`(profession)`
    inline get() = this.villagerData.profession

val VillagerProfession.`(professionId)`
    inline get() = this.name

val VillagerEntity.`(villagerData)`
    inline get() = this.villagerData

val MerchantScreen.`(recipes)`
    get() = this.menu.offers

val MerchantScreen.`(indexStartOffset)`: Int
    get() {
        return this.scrollOff
    }

var MerchantScreen.`(selectedIndex)`: Int
    get() = this.shopItem
    set(value) {
        this.shopItem = value
    }

fun MerchantScreen.`(syncRecipeIndex)`() {
    this.postButtonClick()
}

val MerchantScreen.`(offers)`
    get() = tradeOfferButtons

val ClickableWidget.`(isHovered)`
    get() = isHoveredOrFocused

val TradeOffer.`(originalFirstBuyItem)`
    get() = baseCostA

val TradeOffer.`(secondBuyItem)`: ItemStack?
    get() = costB

val TradeOffer.`(sellItem)`
    get() = result

val TradeOffer.`(isDisabled)`
    get() = isOutOfStock

fun StringNbtReader_parse(s: String): NbtElement? {
    return StringNbtReader.parseTag(s)
}
*/
