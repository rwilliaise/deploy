package com.deploy.api.net;

import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * INetwork interacts with the D.E.P.L.O.Y. network. Only use when you need to
 * get the inventories in a network while still allowing connection.
 */
public interface INetwork {

	/**
	 * Get all connected inventories to the network.
	 * @return All inventories connected to the network.
	 */
	default List<Inventory> getConnectedInventories() {
		List<Inventory> out = new ArrayList<>(this.getAdjacentInventories());
		getConnectedINetworks().forEach(iNetwork -> {
			List<Inventory> added = iNetwork.getAdjacentInventories();
			added.removeAll(out); // remove already existing inventories
			out.addAll(added);
		});
		return out;
	}

	/**
	 * Get all INetwork entities connected to the network.
	 * @return All INetworks in the network
	 */
	default List<INetwork> getConnectedINetworks() {
		List<INetwork> out = new ArrayList<>();
		this.getConnectedINetworks(out, this, null);
		return out;
	}

	/**
	 * Helper function to recursively get all INetwork objects.
	 * @param network List of INetworks to add to
	 * @param target Current INetwork to loop through.
	 * @param ignore Ignore this INetwork when searching.
	 */
	default void getConnectedINetworks(List<INetwork> network, INetwork target, INetwork ignore) {
		for (INetwork net: target.getAdjacentINetworks(ignore)) {
			if (network.contains(net)) { continue; }
			network.add(net);
			this.getConnectedINetworks(network, net, target);
		}
	}

	/**
	 * Get all INetworks that are adjacent to the current INetwork.
	 * @return All adjacent INetworks
	 */
	default List<INetwork> getAdjacentINetworks() {
		return this.getAdjacentINetworks(null);
	}


	List<Inventory> getAdjacentInventories();
	List<INetwork> getAdjacentINetworks(INetwork ignore);
	boolean canConnect(Direction side);
}
