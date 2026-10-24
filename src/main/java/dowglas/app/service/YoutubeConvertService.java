package dowglas.app.service;

import dowglas.app.exception.DownloadErrorException;
import dowglas.app.exception.ProcessBuilderException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YoutubeConvertService {
    public String convert(String url) throws ProcessBuilderException {
        String outputPath = System.getProperty("user.home") + "/Downloads/%(title)s.%(ext)s";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "/home/huemay/.local/bin/yt-dlp",
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

            System.out.println("Erro/Saída do yt-dlp:\n" + output);
            System.out.println("Exit code: " + exitCode);

            if(exitCode != 0) {
               throw new DownloadErrorException("Erro ao baixar e converter o vídeo!! exitCode={} " + exitCode);
            }

            return url;
        } catch (IOException e) {
            throw new ProcessBuilderException(
                    "Não foi possível iniciar o yt-dlp: " + e.getMessage()
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessBuilderException(
                    "A execução foi interrompida."
            );
        }
    }
}
