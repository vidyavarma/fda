package com.nrg.lau.service.batch;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class DeleteAllArchives implements Tasklet{
	
	private static Logger log = Logger.getLogger(DeleteAllArchives.class);
	
	private static final String SQL_DELETE_BATCH_STEP_EXECUTION_CONTEXT = "DELETE FROM BATCH_STEP_EXECUTION_CONTEXT WHERE STEP_EXECUTION_ID IN (SELECT STEP_EXECUTION_ID FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM  BATCH_JOB_EXECUTION where CREATE_TIME < ?))";
	private static final String SQL_DELETE_BATCH_STEP_EXECUTION = "DELETE FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION where CREATE_TIME < ?)";
	private static final String SQL_DELETE_BATCH_JOB_EXECUTION_CONTEXT = "DELETE FROM BATCH_JOB_EXECUTION_CONTEXT WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM  BATCH_JOB_EXECUTION where CREATE_TIME < ?)";
	private static final String SQL_DELETE_BATCH_JOB_EXECUTION_PARAMS = "DELETE FROM BATCH_JOB_EXECUTION_PARAMS WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION where CREATE_TIME < ?)";
	private static final String SQL_DELETE_BATCH_JOB_EXECUTION = "DELETE FROM BATCH_JOB_EXECUTION where CREATE_TIME < ?";
	private static final String SQL_DELETE_BATCH_JOB_INSTANCE = "DELETE FROM BATCH_JOB_INSTANCE WHERE JOB_INSTANCE_ID NOT IN (SELECT JOB_INSTANCE_ID FROM BATCH_JOB_EXECUTION)";
	
	private static final Integer DEFAULT_RETENTION_MONTH = 1;
	private Integer historicRetentionMonth = DEFAULT_RETENTION_MONTH;
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context)
			throws Exception {
		
		deleteEmailLetterArchive(contribution, new JdbcTemplate(dataSource));		
		return RepeatStatus.FINISHED;
	}
	
	private void deleteEmailLetterArchive(StepContribution contribution, JdbcTemplate template) {
		
		int totalCount = 0;
        Date date = DateUtils.addMonths(new Date(), -historicRetentionMonth);
		DateFormat df = new SimpleDateFormat();
        log.info("Remove the Spring Batch history before the { " + df.format(date));

        int rowCount = template.update(SQL_DELETE_BATCH_STEP_EXECUTION_CONTEXT, date);
        log.info("Deleted rows number from the BATCH_STEP_EXECUTION_CONTEXT table: { " + rowCount + " }");
        totalCount += rowCount;

        rowCount = template.update(SQL_DELETE_BATCH_STEP_EXECUTION, date);
        log.info("Deleted rows number from the BATCH_STEP_EXECUTION table: { " + rowCount + " }");
        totalCount += rowCount;

        rowCount = template.update(SQL_DELETE_BATCH_JOB_EXECUTION_CONTEXT, date);
        log.info("Deleted rows number from the BATCH_JOB_EXECUTION_CONTEXT table: { " + rowCount + " }");
        totalCount += rowCount;

        rowCount = template.update(SQL_DELETE_BATCH_JOB_EXECUTION_PARAMS, date);
        log.info("Deleted rows number from the BATCH_JOB_EXECUTION_PARAMS table: { " + rowCount + " }");
        totalCount += rowCount;

        rowCount = template.update(SQL_DELETE_BATCH_JOB_EXECUTION, date);
        log.info("Deleted rows number from the BATCH_JOB_EXECUTION table: { " + rowCount + " }");
        totalCount += rowCount;

        rowCount = template.update(SQL_DELETE_BATCH_JOB_INSTANCE);
        log.info("Deleted rows number from the BATCH_JOB_INSTANCE table: { " + rowCount + " }");
        totalCount += rowCount;

        contribution.incrementWriteCount(totalCount);		
	}
}