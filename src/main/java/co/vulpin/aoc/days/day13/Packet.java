package co.vulpin.aoc.days.day13;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PacketDeserializer.class)
sealed interface Packet extends Comparable<Packet> permits IntPacket, ListPacket {}
