package fi.vm.sade.eperusteet.utils.dto.peruste.lops2019.laajaalainenosaaminen;

import fi.vm.sade.eperusteet.utils.dto.peruste.lops2019.tutkinnonrakenne.KoodiDto;
import fi.vm.sade.eperusteet.utils.dto.utils.LokalisoituTekstiDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lops2019LaajaAlainenOsaaminenDto {
    private Long id;
    private LokalisoituTekstiDto nimi;
    private KoodiDto koodi;
    private LokalisoituTekstiDto kuvaus;
}
