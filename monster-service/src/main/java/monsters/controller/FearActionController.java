package monsters.controller;

import lombok.RequiredArgsConstructor;
import monsters.dto.answer.AnswerFearActionDTO;
import monsters.dto.answer.AnswerMonsterDTO;
import monsters.dto.request.RequestFearActionDTO;
import monsters.mapper.FearActionMapper;
import monsters.service.FearActionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fear-actions")
public class FearActionController {

    private final FearActionService fearActionService;
    private final FearActionMapper fearActionMapper;

    private static final int BUFFER_SIZE = 40;

    @GetMapping("/{fearActionId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AnswerFearActionDTO> getFearAction(@PathVariable UUID fearActionId) {
        return fearActionService.findById(fearActionId)
                .flatMap(fearActionEntity -> Mono.just(fearActionMapper.mapEntityToDto(fearActionEntity)));
    }

    @GetMapping("/date/{date}")
    public Mono<ResponseEntity<Flux<AnswerFearActionDTO>>> findAllByDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date,
                                                                                @RequestParam(defaultValue = "0")
                                                                                @Min(value = 0, message = "must not be less than zero") int page,
                                                                                @RequestParam(defaultValue = "5")
                                                                                @Max(value = 50, message = "must not be more than 50 characters") int size) {

        var fearActionDTOFlux = fearActionService.findAllByDate(date, page, size)
                .buffer(BUFFER_SIZE)
                .flatMap(it -> Flux.fromIterable(it)
                        .map(fearActionMapper::mapEntityToDto)
                        .subscribeOn(Schedulers.parallel()));

        var emptyResponseMono = Mono.just(new ResponseEntity<Flux<AnswerFearActionDTO>>(HttpStatus.NO_CONTENT));
        var responseMono = Mono.just(new ResponseEntity<>(fearActionDTOFlux, HttpStatus.OK));

        return fearActionDTOFlux.hasElements().flatMap(hasElements -> hasElements ? responseMono : emptyResponseMono);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AnswerFearActionDTO> addFearAction(@RequestBody @Valid Mono<RequestFearActionDTO> fearActionDTOMono) {
        return fearActionService.save(fearActionDTOMono)
                .flatMap(fearActionEntity -> Mono.just(fearActionMapper.mapEntityToDto(fearActionEntity)))
                .doOnError(Throwable::printStackTrace);
    }

    @DeleteMapping("/{fearActionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFearAction(@PathVariable UUID fearActionId) {
        return fearActionService.delete(fearActionId);
    }

    @PutMapping("/{fearActionId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AnswerFearActionDTO> putFearAction(@PathVariable UUID fearActionId, @RequestBody @Valid Mono<RequestFearActionDTO> fearActionMono) {
        return fearActionService.updateById(fearActionId, fearActionMono)
                .flatMap(fearActionEntity -> Mono.just(fearActionMapper.mapEntityToDto(fearActionEntity)));
    }

}

