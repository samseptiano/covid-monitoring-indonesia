package com.samseptiano.covidapp.DataClass

import com.google.gson.annotations.SerializedName

data class CovidDataResponse(
	@SerializedName("features")
	var features: MutableList<FeaturesItem?>? = null
//	@SerializedName("globalIdFieldName")
//	val globalIdFieldName: String? = null,
//	@SerializedName("objectIdFieldName")
//	val objectIdFieldName: String? = null,
//	@SerializedName("spatialReference")
//	val spatialReference: SpatialReference? = null,
//	@SerializedName("fields")
//	val fields: List<FieldsItem?>? = null,
//	@SerializedName("uniqueIdField")
//	val uniqueIdField: UniqueIdField? = null,
//	@SerializedName("geometryType")
//	val geometryType: String? = null
)

data class UniqueIdField(
	@SerializedName("isSystemMaintained")
	val isSystemMaintained: Boolean? = null,
	@SerializedName("name")
	val name: String? = null
)

data class Geometry(
	@SerializedName("X")
	val X: Double? = null,
	@SerializedName("Y")
	val Y: Double? = null
)

data class Attributes(
	@SerializedName("FID")
	var FID: Int? = null,
	@SerializedName("Kode_Provi")
	var kodeProvi: Int? = null,
	@SerializedName("Kasus_Meni")
	var kasusMeni: Int? = null,
	@SerializedName("Kasus_Posi")
	var kasusPosi: Int? = null,
	@SerializedName("Provinsi")
	var provinsi: String? = null,
	@SerializedName("Kasus_Semb")
	var kasusSemb: Int? = null
)

data class FeaturesItem(
	var attributes: Attributes? = null,
	val geometry: Geometry? = null
)

data class SpatialReference(
	val latestWkid: Int? = null,
	val wkid: Int? = null
)

data class FieldsItem(
	val sqlType: String? = null,
	val defaultValue: Any? = null,
	val domain: Any? = null,
	val name: String? = null,
	val alias: String? = null,
	val type: String? = null,
	val length: Int? = null
)

