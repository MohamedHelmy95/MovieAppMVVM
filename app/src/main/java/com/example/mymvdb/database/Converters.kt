package com.example.mymvdb.database

import android.util.Log
import androidx.room.TypeConverter
import com.example.mymvdb.movieDetail.Genre
import com.example.mymvdb.movieDetail.ProductionCompany
import com.example.mymvdb.movieDetail.ProductionCountry
import com.example.mymvdb.movieDetail.SpokenLanguage

var defaultGenres: HashSet <Genre> = hashSetOf()
var defaultProductionCountry: HashSet <ProductionCountry> = hashSetOf()
var defaultProductionCompany: HashSet <ProductionCompany> = hashSetOf()
var defaultSpokenLanguage: HashSet <SpokenLanguage> = hashSetOf()
private const val TAG = "Converters"
class Converters {
@TypeConverter
    fun fromGenre(list: List<Genre>?):String{
    var out:String=""
    for (d in list!!){
        out+= "${d.name} , "
        defaultGenres.add(d)
    }
    out= out.removeSuffix(" , ")
    return out
}
    @TypeConverter
    fun toGenre(out:String):List<Genre>{
      var  genreList= emptyList<Genre>()
        var genres=out.toCharArray()
        var  firstIndex:Int=0
        for(d in genres){
            if(d.equals(',')){
                var name=genres.slice(firstIndex  ..  genres.indexOf(d)).toString().replace("[","").replace("]","").replace(",","").replace("\\s".toRegex(),"")
               for (e in defaultGenres){
                   if (e.name == name)
                       genreList+=e
               }
                firstIndex=genres.indexOf(d)
            }
        }
        return genreList
    }

    @TypeConverter
    fun fromProductionCountry(list: List<ProductionCountry>?):String{
        var out:String=""
        for (d in list!!){
            out+= "${d.name} , "
            defaultProductionCountry.add(d)
        }
        out= out.removeSuffix(" , ")
        return out
    }
    @TypeConverter
    fun toProductionCountry(out:String):List<ProductionCountry>{
        var  productionCountryList= emptyList<ProductionCountry>()
        var genres=out.toCharArray()
        var  firstIndex:Int=0
        for(d in genres){
            if(d.equals(',')){
                var name=genres.slice(firstIndex  ..  genres.indexOf(d)).toString().replace("[","").replace("]","").replace(",","").replace("\\s".toRegex(),"")
                for(e in defaultProductionCountry)
                    if (e.name.equals(name))
                        productionCountryList+=e
                Log.d(TAG, "toProductionCountry: name    $name ")

                firstIndex=genres.indexOf(d)
            }
        }
        return productionCountryList
    }

    @TypeConverter
    fun fromProductionCompany(list: List<ProductionCompany>?):String{
        var out:String=""
        for (d in list!!){
            out+= "${d.name} , "
            defaultProductionCompany.add(d)
        }
        out= out.removeSuffix(" , ")
        return out
    }
    @TypeConverter
    fun toProductionCompany(out:String):List<ProductionCompany>{
        var  productionCompanyList= emptyList<ProductionCompany>()
        var companies=out.toCharArray()
        var  firstIndex:Int=0
        for(d in companies){
            if(d.equals(',')){
                var name=companies.slice(firstIndex  ..  companies.indexOf(d)).toString().replace("[","").replace("]","").replace(",","").replace("\\s".toRegex(),"")
                for(e in defaultProductionCompany)
                    if (e.name.equals(name))
                        productionCompanyList+=e
                Log.d(TAG, "toProductionCountry: name    $name ")

                firstIndex=companies.indexOf(d)
            }
        }
        return productionCompanyList
    }
    @TypeConverter
    fun fromSpokenLanguage(list: List<SpokenLanguage>?):String{
        var out:String=""
        for (d in list!!){
            out+= "${d.name} , "
            defaultSpokenLanguage.add(d)
        }
        out= out.removeSuffix(" , ")
        return out
    }
    @TypeConverter
    fun toSpokenLanguage(out:String):List<SpokenLanguage>{
        var  spokenLanguageList= emptyList<SpokenLanguage>()
        var languages=out.toCharArray()
        var  firstIndex:Int=0
        for(d in languages){
            if(d.equals(',')){
                var name=languages.slice(firstIndex  ..  languages.indexOf(d)).toString().replace("[","").replace("]","").replace(",","").replace("\\s".toRegex(),"")
                for(e in defaultSpokenLanguage)
                    if (e.name.equals(name))
                        spokenLanguageList+=e
                Log.d(TAG, "toProductionCountry: name    $name ")

                firstIndex=languages.indexOf(d)
            }
        }
        return spokenLanguageList
    }




}