package org.mocraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import org.mocraft.Block.BlockGenerator;
import org.mocraft.Block.BlockManager;
import org.mocraft.Common.EventManager;
import org.mocraft.Common.QuestManager;
import org.mocraft.Entity.EntityManager;
import org.mocraft.Item.ItemManager;
import org.mocraft.Network.NetworkManager;
import org.mocraft.Utils.PlayerAokIEEP;

@Mod(modid = AgeOfKingdom.MODID, name = AgeOfKingdom.NAME, version = AgeOfKingdom.VERSION)
public class AgeOfKingdom  {

    public static final String NAME = "Age of Kingdom";
    public static final String MODID = "aok";
    public static final String VERSION = "2.0.1558";

    @Mod.Instance(MODID)
    public static AgeOfKingdom INSTANCE;
    public static final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(serverSide = "org.mocraft.ProxyServer", clientSide = "org.mocraft.ProxyClient")
    public static ProxyServer serverProxy;
    public static ProxyClient clientProxy;

    public static BlockManager blockManager = new BlockManager();
    public static ItemManager itemManager = new ItemManager();
    public static NetworkManager packetManager = new NetworkManager();
    public static BlockGenerator blockGenerator = new BlockGenerator();
    public static EventManager eventManager = new EventManager();
    public static EntityManager entityManager = new EntityManager();
    public static QuestManager questManager = new QuestManager();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(event.getSide().isClient()) {
            clientProxy = new ProxyClient();
        }
        serverProxy.preInit(event);
        packetManager.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, serverProxy);
        GameRegistry.registerWorldGenerator(blockGenerator, 0);

        serverProxy.init(event);
        blockManager.init(event);
        itemManager.init(event);
        eventManager.init(event);
        entityManager.init(event);
        questManager.init(event);
        PlayerAokIEEP.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        serverProxy.postInit(event);
    }
}
