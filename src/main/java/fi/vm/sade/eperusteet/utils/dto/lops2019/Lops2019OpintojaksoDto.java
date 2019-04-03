package fi.vm.sade.eperusteet.utils.dto.lops2019;

import fi.vm.sade.eperusteet.utils.dto.util.LokalisoituTekstiDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lops2019OpintojaksoDto {
    private Long id;
    private String koodi;
    @Builder.Default
    private Set<String> oppiaineet = new HashSet<>();
    private LokalisoituTekstiDto nimi;
    private LokalisoituTekstiDto kuvaus;
    private LokalisoituTekstiDto tavoitteet;
    private LokalisoituTekstiDto keskeisetSisallot;
    private LokalisoituTekstiDto laajaAlainenOsaaminen;

    @Singular("moduuli")
    private Set<Lops2019OpintojaksonModuuliDto> moduulit;
}