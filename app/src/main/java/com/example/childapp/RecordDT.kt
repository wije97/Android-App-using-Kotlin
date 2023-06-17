package com.example.childapp

class RecordDT {
    var id: Int=0
    var dateofmes: String?=null
    var weightdt : String?=null
    var heightdt: String?=null
    var monthcount: String?=null
    var child_id:Int?=null

    constructor(dateofmes:String,weightdt:String,heightdt:String,monthcount:String, child_id:Int){
        this.dateofmes=dateofmes
        this.weightdt=weightdt
        this.heightdt=heightdt
        this.monthcount=monthcount
        this.child_id=child_id
    }
}