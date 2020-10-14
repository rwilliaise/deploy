package com.deploy.base;

import com.deploy.base.block.ControllerBlock;
import com.deploy.base.block.entity.ControllerBlockEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeployMod implements ModInitializer {

	public static final String MOD_ID = "deploy";

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(
		new Identifier(MOD_ID, "base"),
		() -> new ItemStack(DeployMod.CONTROLLER));

	public static final Identifier CONTROLLER_ID = new Identifier(MOD_ID, "controller");
	public static final Block CONTROLLER = new ControllerBlock();
	public static final BlockEntityType<?> CONTROLLER_ENTITY = BlockEntityType.Builder.create(ControllerBlockEntity::new, CONTROLLER).build(null);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, CONTROLLER_ID, CONTROLLER);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, CONTROLLER_ID, CONTROLLER_ENTITY);
		Registry.register(Registry.ITEM, CONTROLLER_ID, new BlockItem(CONTROLLER, new Item.Settings().group(GROUP)));
	}
}