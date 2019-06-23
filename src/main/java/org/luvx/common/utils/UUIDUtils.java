package org.luvx.common.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;

import java.util.UUID;
/**
 * @ClassName: org.luvx.common.utils
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/24 9:54
 */
public class UUIDUtils {
    public static String generateUUID() {
        final UUID uuid = Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
        return uuid.toString().replace("-", "");
    }
}
