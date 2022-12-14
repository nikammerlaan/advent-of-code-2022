module com.github.nikammerlaan.aoc {
    requires org.apache.commons.collections4;
    requires com.fasterxml.jackson.databind;

    opens com.github.nikammerlaan.aoc.days.day13 to com.fasterxml.jackson.databind;
}
