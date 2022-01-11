package com.example.demo.generic;

import lombok.extern.apachecommons.CommonsLog;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class S3FileArchiver extends AbstractFileArchiver {



    public S3FileArchiver() {
    }

    @Override
    public String archiveWithTime(String inputPath, String archivePath, Instant archiveInstant) {
        //String archiveKey = SimpleStorageNameUtils.getObjectNameFromLocation(archivePath);
        String formattedArchiveKey = formattedArchiveWithTimePath("archiveKey", archiveInstant);

        archive(inputPath, archivePath, formattedArchiveKey);
        return formattedArchiveKey;
    }

    @Override
    public String archiveWithDate(String inputPath, String archivePath, LocalDate localDate) {
        //String archiveKey = SimpleStorageNameUtils.getObjectNameFromLocation(archivePath);
        String formattedArchiveKey = formattedArchivePath("archiveKey", localDate);

        archive(inputPath, archivePath, formattedArchiveKey);
        return formattedArchiveKey;
    }

    @Override
    public void archive(String inputPath, String archivePath) {
        //archive(inputPath, archivePath, SimpleStorageNameUtils.getObjectNameFromLocation(archivePath));
        archive(inputPath, archivePath, "location/filename_ddMMyyyyhhmmss.csv");
    }

    @Override
    public boolean fileExistsForDate(String fileUrl, LocalDate date) {
        /*S3Utils.S3Asset fileAsset = new S3Utils.S3Asset(fileUrl);
        String inputKey = formattedArchivePath(fileAsset.getObjectName(), date);

        return this.amazonS3Client.doesObjectExist(fileAsset.getBucketName(), inputKey);*/
        return false;
    }

    @Override
    public void delete(String inputPath) {
        /*S3Utils.S3Asset asset = new S3Utils.S3Asset(inputPath);
        if (this.amazonS3Client.doesObjectExist(asset.getBucketName(), asset.getObjectName())) {
            this.amazonS3Client.deleteObject(asset.getBucketName(), asset.getObjectName());
            log.trace(format("Deleted the file from s3, filename=%s", inputPath));
        } else {
            log.info(format("File %s not exist in S3", inputPath));
        }*/
    }

    @Override
    public void archiveWithFilePath(String reportFile, String archivePath) {
        //archive(reportFile, archivePath, SimpleStorageNameUtils.getObjectNameFromLocation(archivePath + SimpleStorageNameUtils.getObjectKeyFromLocation(reportFile)));
    }

    private void archive(String inputPath, String archivePath, String formattedArchiveKey) {

        /*S3Utils.S3Asset asset = new S3Utils.S3Asset(inputPath);

        if (!this.amazonS3Client.doesObjectExist(asset.getBucketName(), asset.getObjectName())) {
            log.info(format("File %s not exist in S3", inputPath));
            return;
        }

        String archivedBucketName = SimpleStorageNameUtils.getBucketNameFromLocation(archivePath);

        this.amazonS3Client.copyObject(asset.getBucketName(), asset.getObjectName(), archivedBucketName, formattedArchiveKey);
        log.info(format("File %s copied to %s. Archived key: %s", inputPath, archivePath, formattedArchiveKey));
        this.amazonS3Client.deleteObject(asset.getBucketName(), asset.getObjectName());
        log.info(format("Original file %s deleted", inputPath));*/
    }

}
