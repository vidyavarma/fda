<?xml version="1.0" encoding="utf-8"?>
<views>
<view id="view11" label="FILTER">
		<pod
			id="MANUFACTURER"
			type="pieChart"
			title="Count by Manufacturer"
			dataSource="LAU_LIST.getManufacturerCount"
			selectedViewIndex="1"
			valueField="COUNT"
			categoryField="NAME"
			labelField="LABEL"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="Mfg"/>
			
		<pod
			id="PRODUCTCODE"
			type="pieChart"
			title="Count by Product Code"
			dataSource="LAU_LIST.getProductCodeCount"
			valueField="COUNT"
			categoryField="NAME"
			labelField="LABEL"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="Pro Code"/>
		<pod
			id="PRODUCTGROUP"
			type="pieChart"
			title="Count by Product Group"
			dataSource="LAU_LIST.getProductGroupCount"
			valueField="COUNT"
			categoryField="NAME"
			labelField="LABEL"
			dataTipUnitLabel="Average time to close reports"
			dataTipLabelField="Pro Group"/>
		<pod
			id="REPORTSTATUS"
			type="pieChart"
			title="Count by Report Status"
			dataSource="LAU_LIST.getReportStatusCount"
			valueField="COUNT"
			categoryField="NAME"
			labelField="LABEL"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="Report Status"/>
		<pod
			id="REFERENCETYPE"
			type="pieChart"
			title="Count by Reference Type"
			dataSource="LAU_LIST.getReferenceTypeCount"
			selectedViewIndex="1"
			valueField="COUNT"
			categoryField="NAME"
			labelField="LABEL"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="Reference Type"/>
		<pod
			id="SUBMITTEDREPORTTYPE"
			type="pieChart"
			title="Count by Type of Report"
			dataSource="LAU_LIST.getSubmittedReportTypeCount"
			valueField="COUNT"
			categoryField="NAME"
			labelField="LABEL"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="Type"/>
	</view>	

</views>