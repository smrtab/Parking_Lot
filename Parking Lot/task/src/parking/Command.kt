package parking

enum class Command(val type: String, val inputSize: Int) {
    PARK("park", 3),
    LEAVE("leave", 2),
    CREATE("create", 2),
    STATUS("status", 1),
    REG_BY_COLOR("reg_by_color", 2),
    SPOT_BY_COLOR("spot_by_color", 2),
    SPOT_BY_REG("spot_by_reg", 2),
    EXIT("exit", 1),
    NULL("", 0);

    companion object {
        fun findByType(type: String): Command {
            for (enum in Command.values()) {
                if (type == enum.type) return enum
            }

            return NULL
        }
        fun isValidType(type: String): Boolean {
            for (enum in Command.values()) {
                if (type == enum.type) return true
            }

            return false
        }
    }

    fun isValidSize(size: Int): Boolean = size == this.inputSize
    fun isExit(): Boolean = this == EXIT
    fun isPark(): Boolean = this == PARK
    fun isLeave(): Boolean = this == LEAVE
    fun isStatus(): Boolean = this == STATUS
    fun isRegByColor(): Boolean = this == REG_BY_COLOR
    fun isSpotByColor(): Boolean = this == SPOT_BY_COLOR
    fun isSpotByReg(): Boolean = this == SPOT_BY_REG

    fun isBlockedUntilCreated(): Boolean {
        return this.isPark() ||
                this.isLeave() ||
                this.isStatus() ||
                this.isRegByColor() ||
                this.isSpotByColor() ||
                this.isSpotByReg()
    }
}