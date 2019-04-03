/*
 * Copyright (c) 2013 The Finnish Board of Education - Opetushallitus
 *
 * This program is free software: Licensed under the EUPL, Version 1.1 or - as
 * soon as they will be approved by the European Commission - subsequent versions
 * of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at: http://ec.europa.eu/idabc/eupl
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * European Union Public Licence for more details.
 */
package fi.vm.sade.eperusteet.utils.dto.peruste;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import fi.vm.sade.eperusteet.utils.domain.Kieli;
import fi.vm.sade.eperusteet.utils.domain.PerusteTila;
import fi.vm.sade.eperusteet.utils.domain.PerusteTyyppi;
import fi.vm.sade.eperusteet.utils.dto.KoulutusDto;
import fi.vm.sade.eperusteet.utils.dto.MuutosmaaraysDto;
import fi.vm.sade.eperusteet.utils.dto.KoodiDto;
import fi.vm.sade.eperusteet.utils.dto.util.LokalisoituTekstiDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jhyoty
 */
@Getter
@Setter
public abstract class PerusteBaseDto implements Serializable {

    private Long id;
    @JsonIgnore
    private Integer revision;
    private PerusteVersionDto globalVersion;

    private LokalisoituTekstiDto nimi;
    private String koulutustyyppi;
    private Set<KoulutusDto> koulutukset;
    private Set<Kieli> kielet;
    private LokalisoituTekstiDto kuvaus;
    private MaarayskirjeDto maarayskirje;
    private List<MuutosmaaraysDto> muutosmaaraykset;

    private String diaarinumero;
    private Date voimassaoloAlkaa;
    private Date siirtymaPaattyy;
    private Date voimassaoloLoppuu;
    private Date paatospvm;

    private Date luotu;
    private Date muokattu;
    private PerusteTila tila;
    private PerusteTyyppi tyyppi;
    private Boolean koulutusvienti;

    private Set<String> korvattavatDiaarinumerot;
    private Set<KoodiDto> osaamisalat;

    // Tuodaan kvliitteestä
    private LokalisoituTekstiDto tyotehtavatJoissaVoiToimia;
    private LokalisoituTekstiDto suorittaneenOsaaminen;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<TutkintonimikeKoodiDto> tutkintonimikkeet;
}