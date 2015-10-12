<?xml version="1.0" encoding="utf-8"?>
<views>
	<view id="view06" label="HOME">
		<pod id="pod61" type="MYREPORT" title="My Open Reports"/>
		<pod id="pod62" type="MYCHECKOUT" title="My Checked Out Reports"/>
	</view>	
	<view id="view1"label="REPORT ENTRY">
		<pod id="pod01" type="MYSTOPS" title="New/Edit Reports"/>		
	</view>
	<view id="view2" label="LIST VIEW">
			<pod id="pod011" type="REPLIST" title="List of Current Reports" />			
	</view>	
		<view id="view03" label="METRICS">
		<pod
			id="podOpenGroup"
			type="pieChart"
			title="Open Reports by User Group"
			dataSource="getOpenGroup.do"
			selectedViewIndex="1"
			valueField="value"
			categoryField="group"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="group"/>
		<pod
			id="podOpenStatus"
			type="pieChart"
			title="Open Reports by Status"
			dataSource="getOpenStatus.do"
			valueField="value"
			categoryField="status"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="status"/>
		<pod
			id="podAvgTimeClose"
			type="lineChart"
			title="Average Time to Close Reports"
			dataSource="getAvgTimeClose.do"
			valueField="days"
			categoryField="month"
			dataTipUnitLabel="Average time to close reports"
			dataTipLabelField="days"/>

		<pod
			id="podOpenByMonth"
			type="columnChart"
			title="Report Promoted/No Actioned/Still Open by Month"
			dataSource="getOpenByMonth.do"
			selectedViewIndex="1"
			valueField1="promoted"
			valueField2="na"
			valueField3="open"
			categoryField="month"
			dataTipUnitLabel="Number of Reports"
			dataTipLabelField="month"/>
	</view>	
	<!--
	<view id="view11"label="QUICK ENTRY">
		<pod id="pod011" type="MYSTOPS" title="New/Edit Reports"/>		
	</view>
	<view id="view2" label="LIST VIEW">
		<pod id="pod011" type="REPLIST" title="List of Current Reports" />			
	</view>
	
	
	<view id="view04" label="REPORT REVIEW">
		<pod id="pod102" type="REPLIST" title="Medical Record Review"/>	
	</view>	
	<view id="view06" label="SUBMISSIONS">
		<pod id="pod103" type="REPLIST" title="Submissions"/>	
	</view>	
	-->	
	<view id="view05" label="ADMINISTRATION">
		<pod id="pod051" type="REPLIST" title="Admin"/>	
	</view>	
</views>