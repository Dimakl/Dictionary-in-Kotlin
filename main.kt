val help_text = "Your command is incorrect. If you need any help with commands type \"help\"\n"
val all_help = "Help:\nYou should split key and value with \"|\"\nCommands list:\nadd - add a key and value in dictionary\nExample:\nadd any_key\"|\"any_key\n" +
        "del - delete item by key or value\nparameters:   -k - delete by key;   -v - delete by value\nExample:\ndel -k any_key\n" +
        "fnd - find item by key or (and) value\nparameters:   -k - find by key;   -v - find by value   -vk (-kv) - find by both key and value (Don't forget to split them with \"|\")" +
        "\nExample:\nfnd -vk any_key|any_value\n" +
        "quit - quit from program\n" +
        "help - see this command list\n"
val quit_str = "Okay :("

var dict = Dictionary()


fun parse_request(req: String) {
    if (req == "help"){
        print(all_help)
        return
    }
    var ind = 0
    while (ind < req.length && req[ind] != ' ')
        ind++
    val cmd = req.subSequence(0,ind).toString()
    if (ind+1 > req.length){
        print(help_text)
        return
    }
    val data = req.substring(ind+1,req.length)
    when (cmd) {
        "add" -> parse_add(data)
        "del" -> parse_del(data)
        "fnd" -> parse_fnd(data)
        else -> print(help_text)
    }
}

fun parse_add(str: String) {
    val ind = str.indexOf('|')
    if (ind == -1){
        print(help_text)
    }
    else {
        if (str.substring(0,ind).length == 0 || str.substring(ind+1,str.length).length == 0) {
            print(help_text)
            return
        }
        val res = dict.add(str.substring(0,ind),str.substring(ind+1,str.length))
        if (res) {
            print("The addition was successful\n")
        }
        else {
            print("The addition can't be done\n")
        }
    }
}

fun parse_del(str: String) {
    var ind = 0
    while (ind < str.length && str[ind] != ' ')
        ind++
    if (ind+1 > str.length){
        print(help_text)
        return
    }
    val param = str.substring(0,ind)
    if (str.substring(ind+1,str.length).length == 0) {
        print(help_text)
        return
    }
    when (param) {
        "-k" -> print(dict.del_key(str.substring(ind+1,str.length)))
        "-v" -> print(dict.del_val(str.substring(ind+1,str.length)))
        else -> print(help_text)
    }
}

fun parse_fnd(str: String) {
    var ind = 0
    while (ind < str.length && str[ind] != ' ')
        ind++
    if (ind+1 > str.length){
        print(help_text)
        return
    }
    var param = str.substring(0,ind)
    if (param == "-kv")
        param = "-vk"
    var ans = mutableListOf<Pair<String,String>>()
    if (str.substring(ind+1,str.length).length == 0){
        print(help_text)
        return
    }
    when (param) {
        "-k" -> ans = dict.find_by_key(str.substring(ind+1,str.length))
        "-v" -> ans = dict.find_by_value(str.substring(ind+1,str.length))
        "-vk" ->
        {
            val split = str.indexOf('|')
            if (split == -1){
                print(help_text)
                return
            }
            if (str.substring(ind+1,split).length == 0 || str.substring(split+1,str.length).length == 0)
                print(help_text)
            else
                ans = dict.find_by_vk(str.substring(ind+1,split),str.substring(split+1,str.length))
        }
        else ->
        {
            print(help_text)
            return
        }
    }
    if (ans.size == 0){
        print("None was found\n")
        return
    }
    for (elem in ans){
        print("${elem.first}|${elem.second}\n")
    }
}

fun main(args: Array<String>) {
    var inp = readLine()!!
    while (inp != "quit") {
        parse_request(inp)
        inp = readLine()!!
    }
    print(quit_str)
}
