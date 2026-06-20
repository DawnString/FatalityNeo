package cn.dawnstring.fatality.guide.data;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GuideEntry(
        ResourceLocation id,
        String name,
        ResourceLocation icon,
        ResourceLocation category,
        int sortnum,
        List<GuidePage> pages
) {}
