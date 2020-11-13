package parking

data class Car(val registrationNumber: String, val color: String) {
    override fun toString(): String {
        return "${this.registrationNumber} ${this.color}"
    }
}