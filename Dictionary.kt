// Knuth Morris Pratt algoritm was used for search. You can type "help" for help and list of commands.

class Dictionary {

    private var kvm = hashMapOf<String,String>() // Key for Value Map
    private var vkm = hashMapOf<String,String>() // Value for Key Map
    private var bad_val = 0

    fun add(key: String, value: String): Boolean {
        if (kvm[key] != null)
            return false
        kvm.put(key,value)
        vkm.put(value,key)
        return true
    }

    fun del_key(key: String): String {
        val value = kvm[key]
        if (value == null)
            return "The removal can't be done\n"
        kvm.remove(key)
        vkm.remove(value)
        bad_val++

        return "The removal was successful\n"
    }

    fun del_val(value: String): String {
        val key = vkm[value]
        if (key == null)
            return "The removal can't be done\n"
        vkm.remove(value)
        kvm.remove(key)
        bad_val++

        return "The removal was successful\n"
    }

    fun find_by_key(key_frag: String): MutableList<Pair<String,String>> {
        var good = mutableListOf<Pair<String,String>>()
        for (key in kvm.keys) {
            if (KMP(key,key_frag)) {
                good.add(Pair(key,kvm[key]!!))
            }
        }
        return good
    }

    fun find_by_value(val_frag: String): MutableList<Pair<String,String>> {
        var good = mutableListOf<Pair<String,String>>()
        for (value in vkm.keys) {
            if (KMP(value,val_frag)) {
                good.add(Pair(vkm[value]!!,value))
            }
        }
        return good
    }

    fun find_by_vk(key_frag: String, val_frag: String): MutableList<Pair<String,String>> {
        var good = mutableListOf<Pair<String,String>>()
        for (key in kvm.keys) {
            if (KMP(key,key_frag)) {
                if (KMP(kvm[key]!!,val_frag))
                    good.add(Pair(key,kvm[key]!!))
            }
        }
        return good
    }

    private fun make_pref(value: String): IntArray {
        var pref = IntArray(value.length)
        pref[0] = 0
        var k = 0
        var ind = 1
        while (ind < value.length) {
            while ((k > 0) && (value[k] != value[ind]))
                k = pref[k-1]
            if (value[k] == value[ind])
                k++
            pref[ind] = k
            ind++
        }
        return pref
    }

    private fun KMP(fnd: String, str: String): Boolean {
        var pref = make_pref(str)
        var k = 0
        var i = 0

        while (i < fnd.length) {
            while (k > 0 && str[k] != fnd[i])
                k = pref[k-1]
            if (str[k] == fnd[i])
                k++
            if (k == str.length)
                return true
            i++
        }
        return false
    }
}