package com.github.xingshuangs.iot.protocol.s7.serializer;

import com.github.xingshuangs.iot.protocol.s7.enums.EPlcType;
import com.github.xingshuangs.iot.protocol.s7.service.S7PLC;
import com.github.xingshuangs.iot.utils.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

@Slf4j
@Ignore
public class S7SerializerTest {
    private final S7PLC s7PLC = new S7PLC(EPlcType.S1200, "127.0.0.1");

    @Test
    public void read() {
        s7PLC.setComCallback(x -> System.out.printf("长度[%d]:%s%n", x.length, HexUtil.toHexString(x)));
//        s7PLC.setComCallback(x -> log.debug("长度[{}]，内容：{}", x.length, HexUtil.toHexString(x)));
        S7Serializer s7Serializer = S7Serializer.newInstance(s7PLC);
        DemoBean bean = s7Serializer.read(DemoBean.class);
        log.info(bean.toString());
    }

    @Test
    public void write() {
        s7PLC.setComCallback(x -> log.debug("长度[{}]，内容：{}", x.length, HexUtil.toHexString(x)));
        S7Serializer s7Serializer = S7Serializer.newInstance(s7PLC);
        byte[] byteData = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03};
        DemoBean bean = new DemoBean();
        bean.setBitData(true);
        bean.setUint16Data(42767);
        bean.setInt16Data((short) 32767);
        bean.setUint32Data(3147483647L);
        bean.setInt32Data(2147483647);
        bean.setFloat32Data(3.14f);
        bean.setFloat64Data(4.15);
        bean.setByteData(byteData);
        bean.setStringData("1234567890");
        bean.setTimeData(12L);
        bean.setDateData(LocalDate.of(2023, 5, 15));
        bean.setTimeOfDayData(LocalTime.of(20, 22, 13));
        bean.setDateTimeData(LocalDateTime.of(2023, 5, 27, 12, 11, 22, 333225555));
        s7Serializer.write(bean);
        DemoBean actual = s7Serializer.read(DemoBean.class);
        assertTrue(actual.getBitData());
        assertEquals(42767, actual.getUint16Data().intValue());
        assertEquals(32767, actual.getInt16Data().intValue());
        assertEquals(3147483647L, actual.getUint32Data().longValue());
        assertEquals(2147483647, actual.getInt32Data().intValue());
        assertEquals(3.14f, actual.getFloat32Data(), 0.001);
        assertEquals(4.15, actual.getFloat64Data(), 0.001);
        assertArrayEquals(byteData, actual.getByteData());
        assertEquals("1234567890", actual.getStringData());
        assertEquals(12, actual.getTimeData().longValue());
        assertEquals(LocalDate.of(2023, 5, 15), actual.getDateData());
        assertEquals(LocalTime.of(20, 22, 13), actual.getTimeOfDayData());
        assertEquals(LocalDateTime.of(2023, 5, 27, 12, 11, 22, 333225555), actual.getDateTimeData());
    }

    @Test
    public void writeLargeData() {
        s7PLC.setComCallback(x -> log.debug("长度[{}]，内容：{}", x.length, HexUtil.toHexString(x)));
        S7Serializer s7Serializer = S7Serializer.newInstance(s7PLC);
        DemoLargeBean bean = s7Serializer.read(DemoLargeBean.class);
        System.out.println("-------------------------------");
        bean.setBitData(true);
        bean.getByteData2()[0] = (byte) 0x05;
        bean.getByteData3()[0] = (byte) 0x05;
        bean.getByteData4()[0] = (byte) 0x05;
        bean.getByteData5()[0] = (byte) 0x05;
        bean.getByteData6()[0] = (byte) 0x05;
        bean.getByteData7()[0] = (byte) 0x05;
        bean.getByteData2()[64] = (byte) 0x02;
        bean.getByteData3()[199] = (byte) 0x03;
        bean.getByteData4()[321] = (byte) 0x04;
        bean.getByteData5()[98] = (byte) 0x05;
        bean.getByteData6()[499] = (byte) 0x06;
        bean.getByteData7()[43] = (byte) 0x07;
        s7Serializer.write(bean);
    }

    @Test
    public void dbData() {
        s7PLC.setComCallback(x -> log.debug("长度[{}]，内容：{}", x.length, HexUtil.toHexString(x)));
        S7Serializer s7Serializer = S7Serializer.newInstance(s7PLC);
        DB80 bean = s7Serializer.read(DB80.class);
        System.out.println(bean);
    }
}