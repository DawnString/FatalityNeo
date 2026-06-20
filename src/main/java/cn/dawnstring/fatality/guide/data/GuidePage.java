package cn.dawnstring.fatality.guide.data;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public sealed interface GuidePage
{
    String title();
    String type();

    record Text(String title, String text, List<ResourceLocation> items) implements GuidePage
    {
        @Override
        public String type() { return "text"; }
    }

    record ItemSpotlight(String title, ResourceLocation item, String text) implements GuidePage
    {
        @Override
        public String type() { return "item_spotlight"; }
    }

    record Entity(String title, ResourceLocation entity, String text) implements GuidePage
    {
        @Override
        public String type() { return "entity"; }
    }

    record Recipe(String title, ResourceLocation recipe) implements GuidePage
    {
        @Override
        public String type() { return "recipe"; }
    }
}
