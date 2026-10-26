package dowglas.app.service;

import ch.qos.logback.classic.Logger;
import dowglas.app.exception.DownloadErrorException;
import dowglas.app.exception.ProcessBuilderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class YoutubeConvertService {
    public String convert(String url) throws ProcessBuilderException {
        String downloadDir = System.getenv()
                .getOrDefault("DOWNLOAD_DIR",
                        System.getProperty("user.home") + "/Downloads");

        String outputPath = downloadDir + "/%(title)s.%(ext)s";

        String ytDlpPath = System.getenv()
                .getOrDefault("YT_DLP_PATH", "yt-dlp");

        ProcessBuilder processBuilder = new ProcessBuilder(
                ytDlpPath,
                "--js-runtimes", "deno",
                "--remote-components", "ejs:npm",
                "-x",
                "--audio-format", "mp3",
                "--audio-quality", "192K",
                "-o", outputPath,
                url
        );

        processBuilder.redirectErrorStream(true);

        try{
            java.nio.file.Files.createDirectories(
                    java.nio.file.Path.of(downloadDir)
            );

            Process process = processBuilder.start();

            String output;

            try (var reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(
                            process.getInputStream(),
                            java.nio.charset.StandardCharsets.UTF_8
                    ))) {

                output = reader.lines()
                        .collect(java.util.stream.Collectors.joining("\n"));
            }

            int exitCode = process.waitFor();

            log.info("Erro/Saída do yt-dlp:\n" + output);
            log.info("Exit code: " + exitCode);

            if(exitCode != 0) {
               throw new DownloadErrorException("Erro ao baixar e converter o vídeo!! exitCode={} " + exitCode);
            }

            return url;
        } catch (IOException e) {
            throw new ProcessBuilderException("Não foi possível iniciar o yt-dlp: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();throw new ProcessBuilderException("A execução foi interrompida.");
        }
    }
}
