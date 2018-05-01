import java.util.*

class Dictionary {



    var kvm = hashMapOf<String,String>() // Key for Value Map
    var vkm = hashMapOf<String,String>() // Value for Key Map
    var spm = hashMapOf<String,IntArray>() // Something for Prefix Map

    fun add(key: String, value: String): Boolean {
        if (kvm[key] != null)
            return false
        kvm.put(key,value)
        vkm.put(value,key)
        spm.put(key,make_pref(key))
        spm.put(value,make_pref(value))

        return true
    }

    fun del_key(key: String): Boolean {
        val value = kvm[key]
        if (value == null)
            return false
        kvm.remove(key)
        vkm.remove(value)
        spm.remove(key)
        // can't remove value from SKM, because it might be used in other pairs

        return true
    }

    fun del_val(value: String): Boolean {
        val key = vkm[value]
        if (key == null)
            return false
        vkm.remove(value)
        kvm.remove(key)
        spm.remove(key)
        // can't remove value from SKM, because it might be used in other pairs

        return true
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
}