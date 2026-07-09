package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "certificate_of_valor", category = ItemCategory.ACCESSORY)
public class CertificateOfValor extends AccessoryItem
{
    public CertificateOfValor()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f),
                new StatModifier("recoverHealthSpeedBonus", 0.05f)
        ));
    }
}
