package cn.mcres.karlatemp.mojangyggdrasil.Config;

import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.PlayerSaveObj;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SkinOBJ;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PlayerConfig {
    public static PlayerSaveObj playerUuid = new PlayerSaveObj();
    public static File file;

    public static String getUUID(String id, String uuid) {
        if (playerUuid.getPlayers().containsKey(id)) {
            if (playerUuid.getPlayers().get(id).isEmpty()) {
                SetPlayer(id, uuid);
                return uuid;
            }
            return playerUuid.getPlayers().get(id);
        } else if (playerUuid.getPlayers().containsValue(uuid)) {
            SetPlayer(id, uuid);
            return uuid;
        } else {
            AddPlayer(id, uuid);
            return uuid;
        }
    }

    public static void RemoveSkin(String player) {
        playerUuid.removeSkin(player);
        save();
    }

    public static SkinOBJ getSKin(String player) {
        return playerUuid.getSkin(player);
    }

    public static void SetSkin(String player, SkinOBJ skin) {
        playerUuid.setSkin(player, skin);
        save();
    }

    public static boolean haveName(String id) {
        return playerUuid.getPlayers().containsKey(id);
    }

    public static void SetPlayer(String id, String uuid) {
        Map<String, String> temp = new HashMap<>(playerUuid.getPlayers());
        String old = "";
        for (Map.Entry<String, String> item : temp.entrySet()) {
            if (item.getValue().equals(uuid)) {
                old = item.getKey();
                break;
            }
        }
        Loggin.boot.info("存在相同的UUID:" + uuid + ",已将" + old + "转换为" + id);
        playerUuid.RemovePlayer(id);
        playerUuid.AddPlayers(id, uuid);
        save();
    }

    public static void AddPlayer(String id, String uuid) {
        playerUuid.AddPlayers(id, uuid);
        save();
    }

    public static void AddBanID(String id) {
        playerUuid.AddBanID(id);
        save();
    }

    public static void AddBanUUID(String uuid) {
        playerUuid.AddBanUUID(uuid);
        save();
    }

    public static void RemoveBanID(String id) {
        playerUuid.RemoveBanID(id);
        save();
    }

    public static void RemoveBanUUID(String uuid) {
        playerUuid.RemoveBanUUID(uuid);
        save();
    }

    private static void save() {
        try {
            String data = new Gson().toJson(playerUuid);
            if (file.exists()) {
                Writer out = new FileWriter(file);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isBan(String id, String uuid) {
        return playerUuid.getBanID().contains(id) || playerUuid.getBanUUID().contains(uuid);
    }

    public static boolean HavePlayer(String id) {
        return playerUuid.havePlayer(id);
    }

    public static void RemovePlayer(String id) {
        playerUuid.RemoveID(id);
        save();
    }
}
