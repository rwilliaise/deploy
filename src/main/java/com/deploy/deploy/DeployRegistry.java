package com.deploy.deploy;

import com.deploy.deploy.item.IdentifierItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DeployRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DeployMod.MOD_ID);

	public static final RegistryObject<Item> CALCULATOR_ITEM = ITEMS.register("identifier", 
		() -> new IdentifierItem(new Item.Properties().group(ItemGroup.MISC)));

	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
