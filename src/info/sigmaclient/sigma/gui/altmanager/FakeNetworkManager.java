package info.sigmaclient.sigma.gui.altmanager;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;

import java.nio.channels.Channel;

public class FakeNetworkManager extends NetworkManager
{
    public FakeNetworkManager(final PacketDirection packetDirection)
    {
        super(packetDirection);
    }
    
    public Channel channel() {
        return new Channel() {
            @Override
            protected void finalize() {
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public void close() {

            }
        };
    }
}