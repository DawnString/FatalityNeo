package cn.dawnstring.fatality.register;

import cn.dawnstring.fatality.item.ItemCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoItem
{
    String itemId();
    ItemCategory category() default ItemCategory.MISC;
}
