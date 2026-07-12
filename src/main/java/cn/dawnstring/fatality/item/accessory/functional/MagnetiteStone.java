package cn.dawnstring.fatality.item.accessory.functional;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoItem(itemId = "magnetite_stone", category = ItemCategory.ACCESSORY)
public class MagnetiteStone extends AccessoryItem
{
    public MagnetiteStone()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide()) return;

        AABB area = player.getBoundingBox().inflate(5);
        Vec3 target = player.position().add(0, 0.75, 0);

        for (ItemEntity item : player.level().getEntitiesOfClass(ItemEntity.class, area, ItemEntity::isAlive))
        {
            Vec3 itemPos = item.position();
            double dist = itemPos.distanceTo(target);

            if (dist < 1.5)
                item.setPickUpDelay(0);

            if (dist > 0.5)
            {
                Vec3 pull = target.subtract(itemPos).normalize().scale(0.04);
                item.setDeltaMovement(item.getDeltaMovement().add(pull));
            }
        }
    }
}
