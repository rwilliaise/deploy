package com.deploy.base;

import com.deploy.base.block.CableBlock;
import com.deploy.base.block.ControllerBlock;
import com.deploy.base.block.entity.CableBlockEntity;
import com.deploy.base.block.entity.ControllerBlockEntity;

import com.deploy.base.net.DeployNet;
import com.deploy.base.screen.ControllerScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeployMod implements ModInitializer {

	public static final String MOD_ID = "deploy";

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(
		new Identifier(MOD_ID, "base"),
		() -> new ItemStack(DeployMod.CONTROLLER_BLOCK));

	public static final Identifier CONTROLLER = new Identifier(MOD_ID, "controller");
	public static final Block CONTROLLER_BLOCK = new ControllerBlock();
	public static final BlockEntityType<?> CONTROLLER_ENTITY = BlockEntityType.Builder.create(ControllerBlockEntity::new, CONTROLLER_BLOCK).build(null);
	public static final ScreenHandlerType<?> CONTROLLER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(CONTROLLER, ControllerScreenHandler::new);

	public static final Identifier CABLE_ID = new Identifier(MOD_ID, "cable");
	public static final Block CABLE = new CableBlock();
	public static final BlockEntityType<?> CABLE_ENTITY = BlockEntityType.Builder.create(CableBlockEntity::new, CABLE).build(null);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, CONTROLLER, CONTROLLER_BLOCK);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, CONTROLLER, CONTROLLER_ENTITY);
		Registry.register(Registry.ITEM, CONTROLLER, new BlockItem(CONTROLLER_BLOCK, new Item.Settings().group(GROUP)));

		Registry.register(Registry.BLOCK, CABLE_ID, CABLE);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, CABLE_ID, CABLE_ENTITY);
		Registry.register(Registry.ITEM, CABLE_ID, new BlockItem(CABLE, new Item.Settings().group(GROUP)));

		DeployNet.onServerInitialize();
	}
}
