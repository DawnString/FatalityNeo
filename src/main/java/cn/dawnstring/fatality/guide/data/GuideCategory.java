package cn.dawnstring.fatality.guide.data;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GuideCategory(
        ResourceLocation id,
        String name,
        ResourceLocation icon,
        int sortnum,
        ResourceLocation parentId,
        List<ResourceLocation> entries
) {}
