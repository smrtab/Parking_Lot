package parking

import java.util.*
import kotlin.Exception

fun main() {
    val scanner = Scanner(System.`in`)
    val parkingLot = ParkingLot()

    while (scanner.hasNextLine()) {

        val request = scanner.nextLine()

        try {
            parkingLot.handle(request)
        } catch (e: ParkingLotException) {
            println(e.message)
        } catch (e: ExitCommandException) {
            break
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
